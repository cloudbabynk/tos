/**
 *
 *
 **/
import _ from 'lodash';
import zrender from 'zrender';
import uuid from 'uuid/v1';
import $ from 'jquery';
import BerthShip from 'BerthShip';

let obj = {};
let self = {};

class ShipBerth {
    opt;
    baseGroup;
    zrender;
    data;

    constructor(idName, option, data) {
        self = this;
        this.baseGroup = new zrender.Group({ name: 'base' });
        obj.crossGroup = '';
        obj.crossTextGroup = '';
        obj.preAddShipGroup = '';
        obj.curShipGroupId = '';
        obj.curBorderId = '';
        obj.hdShipDragging = false;
        obj.savedShipPosition = {};
        obj.timeout = false;
        obj.xData = [];
        obj.yData = [];
        obj.xLength = 0;
        obj.yLength = 0;
        obj.hdLineDragging = false;
        obj.preAddShipData = {};
        obj.preAddShipCallback = false;
        obj.preAddShipSaveHandler = false;
        obj.zeroPosition = [];
        obj.leftTopPosition = [];
        obj.shipMouseoverFlag = false;
        let xAxis = {
            interval: 25,
            scale: 2, // 比例尺 1px 代表xx米
            height: 30,
        };
        let yAxis = {
            interval: 8.5,
        };
        let shipOpt = {
            height: 30,
            prowWidth: 30, // 船头宽度
            circleR: 5,
        };
        let preAddOpt = {
            flag: false,
            width: 100,
            height: 50,
        };
        /**
         * 配置
         * @type {{}}
         * **/
        let opt = {
            random: uuid(),
            xAxis,
            yAxis,
            shipOpt,
            shipDbClick: false,
            shipSaveHandler: false,
            shipContextmenu: false,
            shipMouseover: false,
            shipMouseout: false,
            preAddOpt,
        };
        // 合并配置
        this.opt = _.merge(opt, option);
        obj = {};
        // 初始化zrender
        this.zrender = zrender.init(document.getElementById(idName));
        this.data = data;
    }

    /**
     * 绘制
     * @param data
     */
    draw(data, opt) {
        self.data = data;
        if (opt && opt.xScale) {
            self.opt.xAxis.scale = opt.xScale;
        }
        this.baseGroup.removeAll();
        this._setZrenderSize(data.berthAxisInfoList, data.timeAxisInfo);
        this._drawX(data.berthAxisInfoList);
        this._drawY(data.timeAxisInfo);
        this._drawCenter();
        this._drawShips(data.shipList);
    }

    /**
     * 添加船以及相关事件
     * @param ship
     * @param newFlag
     * @returns {boolean}
     */
    addOneShip(ship, newFlag) {

        let group = new zrender.Group({ name: ship.shipVisitId });
        let shipGroup = new zrender.Group({
            name: 'shipBox',
        });
        // 确定坐标位置
        // x轴
        group.xScope = [];
        let xObj = [];
        xObj[0] = this._findXPositionByCode(ship.planBerthCode, ship.planBeginBollardCode);
        xObj[1] = this._findXPositionByCode(ship.planBerthCode, ship.planEndBollardCode);

        if (!xObj[0] || !xObj[1]) {
            return;
        }
        if (!ship.shipLength) {
            ship.shipLength = (xObj[1].pos - xObj[0].pos) * self.opt.xAxis.scale;
        }
        if (newFlag) {
            // 新增船，如果width 比船小，增加一个揽桩号
            this._resizeXPosByShipLength(xObj, ship);
        }
        // 使用 etb etu 转换
        if (!ship.etb || !ship.etu) {
            return;
        } else {
            try {
                ship.planBerthStartTimestamp = new Date(ship.etb).getTime();
                ship.planBerthEndTimestamp = new Date(ship.etu).getTime();
            } catch (e) {
                console.log('时间转换错误', e);
                return;
            }
        }
        if (ship.planBerthStartTimestamp < obj.yData[0].timestamp || ship.planBerthEndTimestamp > obj.yData[obj.yData.length - 1].timestamp) {
            return;
        }
        group.xScope[0] = xObj[0].pos;
        group.xScope[1] = xObj[1].pos;
        // y轴
        group.yScope = [];
        group.yScope[0] = this._findYPositionByTime(ship.planBerthStartTimestamp);
        group.yScope[1] = this._findYPositionByTime(ship.planBerthEndTimestamp);
        let height = group.yScope[0] - group.yScope[1];
        let width = group.xScope[1] - group.xScope[0];

        // 赋值 揽桩 name
        ship.planBeginBollardName = obj.xData[xObj[0].index].bollardName;
        ship.planEndBollardName = obj.xData[xObj[1].index].bollardName;
        shipGroup.add(this._drawShipText(ship, width, height));
        // 舷靠方式
        let arrow = ship.planBerthingMode;
        // 船体
        if (!ship.sortNo) {
            ship.sortNo = '';
        }
        let berthShip = new BerthShip({
            name: 'berthShip',
            draggable: true,
            shape: {
                x: 0,
                y: 0,
                width: ship.shipLength / self.opt.xAxis.scale,
                height: height,
                arrow: arrow,
            },
            style: {
                fill: '#7fc3fc',
                stroke: '#272cff',
                opacity: 1,
                shadowBlur: 5,
                shadowColor: '#272cff',
                textLineHeight: 25,
                text: '{no|' + ship.sortNo + '}\n{info|' + ship.shipCode + '/' + ship.imVoyage + '/' + ship.exVoyage + '}',
                rich: {
                    no: {
                        fontSize: 22,
                        textFill: '#d90000',
                        textAlign: 'center',
                    },
                    info: {
                        fontSize: 16,
                        textFill: '#000',
                    }
                }
            }

        });


        shipGroup.add(berthShip);
        // 船首船尾圆点
        let beginCircle = new zrender.Circle({
            name: 'beginCircle',
            shape: {
                cx: width,
                cy: height / 2,
                r: self.opt.shipOpt.circleR,
            },
            style: {
                fill: '#fff',
                stroke: '#431eff',
            }
        });
        shipGroup.add(beginCircle);
        let endCircle = new zrender.Circle({
            name: 'endCircle',
            shape: {
                cx: 0,
                cy: height / 2,
                r: self.opt.shipOpt.circleR,
            },
            style: {
                fill: '#fff',
                stroke: '#431eff',
            }
        });
        shipGroup.add(endCircle);

        shipGroup.add(this._drawChangeBorder(ship, group));
        group.add(shipGroup);
        // 鼠标按下
        group.on('mousedown', function (event) {
            // 左键按下
            if (event.which === 1) {
                // 拖拽标志
                obj.hdShipDragging = true;
                berthShip.draggable = true;
                self._saveCurPosition();
            } else {
                obj.hdShipDragging = false;
                berthShip.draggable = false;
            }
        });
        $(window).on('mouseup', { group }, function (event) {
            if (!obj.hdLineDragging && obj.hdShipDragging && obj.curShipGroupId === event.data.group.id) {
                event.data.group.trigger('mouseup');
            }
            if (!obj.curShipGroupId) {
                obj.hdShipDragging = false;
            }
        });
        // 鼠标按键抬起
        group.on('mouseup', function (event) {
            if (obj.crossGroup && obj.hdShipDragging) {
                if (obj.curShipGroupId === this.id) {
                    console.log('mouseup ship save');
                    obj.crossTextGroup.removeAll();
                    obj.hdShipDragging = false;
                    obj.curShipGroupId = '';
                    let overBorder = false;
                    let positionChange = false;// 检测坐标轴数据是否变动
                    obj.crossGroup.eachChild(function (ele) {
                        if (ele.hdOverBorder) {
                            overBorder = true;
                            return;
                        } else {
                            if (obj.savedShipPosition[ele.name] !== ele.hdindex) {
                                positionChange = true;
                            }
                        }
                    });

                    if (overBorder || !positionChange) {
                        // 超出边界恢复上次位置
                        self._freshShipPosition(this);
                    } else {
                        // 更新ship的 坐标位置
                        self._updateShipXY(ship, this);
                        self._freshShipPosition(this);
                        if (typeof self.opt.shipSaveHandler === 'function') {
                            self.opt.shipSaveHandler(ship);
                        }
                    }
                }
            }

        });
        // 鼠标移动
        group.on('mousemove', function (event) {
            if (obj.crossGroup && !obj.hdShipDragging && !obj.hdLineDragging) {
                if (!obj.curShipGroupId || obj.curShipGroupId !== this.id) {
                    self._shipCrossLines(ship, event, this);
                    if (typeof self.opt.shipMouseover === 'function') {
                        self.opt.shipMouseover(event, ship);
                        obj.shipMouseoverFlag = true;
                    }
                }
            }
            if (obj.hdLineDragging) {
                obj.crossGroup.removeAll();
                let xCross = self._getXCrossLine(event.offsetX);
                obj.crossGroup.add(xCross);
                let yCross = self._getYCrossLine(event.offsetY);
                obj.crossGroup.add(yCross);
            }
        });
        // 拖拽时
        group.on('drag', function (event) {
            self._hideShipMouseOver();
            // 其他元素移动
            let shipBox = this.childOfName('shipBox');
            shipBox.eachChild(function (ele) {
                ele.position = [event.target.position[0], event.target.position[1]];
                ele.dirty();
            });
            self._shipCrossLines(ship, event, this);
            // 更新shiptext值
            self._shipCrossText(ship, event, this);
        });
        group.on('dblclick', function (event) {
            console.log('dbl');
            if (self.opt.shipDbClick) {
                self.opt.shipDbClick.call(this, ship, event);
            }
            self._hideShipMouseOver();
        });
        group.on('contextmenu', function (event) {
            // 阻止默认事件
            event.event.preventDefault();
            if (typeof self.opt.shipContextmenu === 'function') {
                self.opt.shipContextmenu(event, ship);
            }
            self._hideShipMouseOver();
        });
        group.position = [group.xScope[0], group.yScope[1]];
        group.hddata = {};
        group.hddata.ship = ship;
        // group.orginShip = _.cloneDeep(ship);
        this.baseGroup.add(group);
        if (newFlag) {
            self.data.shipList.push(ship);
        }
        return true;
    }

    _saveCurPosition() {
        obj.savedShipPosition = {};
        let eles = obj.crossGroup.children();
        console.log(eles.length);
        if (eles.length === 4) {
            // 保存
            for (let i = 0; i < eles.length; i++) {
                let ele = eles[i];
                obj.savedShipPosition[ele.name] = ele.hdindex;
            }
        }
    }

    refreshOneShip(ship) {
        self.baseGroup.remove(self.baseGroup.childOfName(ship.shipVisitId));
        self.addOneShip(ship);
    }
    removeOneShip(shipId) {
        self.baseGroup.remove(self.baseGroup.childOfName(shipId));
        let index = -1;
        for (let i = 0; i < self.data.shipList.length; i++) {
            let ship = self.data.shipList[i];
            if (ship.shipVisitId === shipId) {
                index = i;
            }
        }
        if (index !== -1) {
            self.data.shipList.splice(index, 1);
        }
    }

    preAddShip(ship, callback, preAddShipSaveHandler) {
        self.opt.preAddOpt.flag = true;
        obj.preAddShipData = ship;
        obj.preAddShipCallback = callback;
        obj.preAddShipSaveHandler = preAddShipSaveHandler;
        // 计算self.opt.preAddOpt.width
        let lengthMeter = ship.shipLength;
        if (lengthMeter) {
            self.opt.preAddOpt.width = lengthMeter / self.opt.xAxis.scale;
        }
    }

    _hideShipMouseOver(event) {
        if (obj.shipMouseoverFlag) {
            if (typeof self.opt.shipMouseout === 'function') {
                self.opt.shipMouseout(event);
            }
            obj.shipMouseoverFlag = false;
        }
    }

    _setZrenderSize(xArray, yArray) {
        let height = (yArray.times.length - 1) * self.opt.yAxis.interval;
        height = height + 40 + self.opt.xAxis.height;
        // 根据米数和比例尺换算
        // 总长 最后一个揽桩的距离
        let width = 0;
        for (let i = 0; i < xArray.length; i++) {
            let berthInfo = xArray[xArray.length - 1 - i];
            if (berthInfo.bollardList.length > 0) {
                width = berthInfo.bollardList[berthInfo.bollardList.length - 1].bollardDistance;
                break;
            }
        }
        width = width / self.opt.xAxis.scale;
        obj.xLength = width;
        width += 150;
        this.zrender.resize({
            height,
            width,
        });
        obj.zeroPosition = [130, height - 30 - self.opt.xAxis.height];
    }

    _drawX(array) {
        let group = new zrender.Group({ name: 'xAxis' });
        obj.xData = [];
        let allLength = obj.xLength;
        // 泊位长度
        let line = new zrender.Line({
            shape: {
                x1: 0,
                y1: 0,
                x2: allLength,
                y2: 0,
            },
            style: {
                lineWidth: 5,
                text: '',
                textPosition: 'left',
                textOffset: [0, 20],
            }
        });
        group.add(line);
        let lastBerthWidth = 0;
        for (let i = 0; i < array.length; i++) {
            let berthInfo = array[i];
            if (!berthInfo.bollardList || berthInfo.bollardList.length === 0) {
                continue;
            }
            obj.xData = obj.xData.concat(berthInfo.bollardList);
            let berthGroup = new zrender.Group({ name: 'berth' });
            // 泊位名称
            let rect = new zrender.Rect({
                shape: {
                    x: lastBerthWidth,
                    y: 0,
                    width: berthInfo.bollardList[berthInfo.bollardList.length - 1].bollardDistance / self.opt.xAxis.scale - lastBerthWidth,
                    height: self.opt.xAxis.height,
                },
                style: {
                    text: berthInfo.berth.berthName + ' ' + berthInfo.waterDepth,
                    textPosition: 'bottom',
                    textVerticalAlign: 'top',
                    fill: '#fff',
                    stroke: '#272cff',
                }
            });
            berthGroup.add(rect);
            group.add(berthGroup);
            // 缆桩
            for (let i = 0; i < berthInfo.bollardList.length; i++) {
                let bollard = berthInfo.bollardList[i];
                let line = new zrender.Line({
                    shape: {
                        x1: bollard.bollardDistance / self.opt.xAxis.scale,
                        y1: 0,
                        x2: bollard.bollardDistance / self.opt.xAxis.scale,
                        y2: 10,
                    },
                    style: {
                        text: bollard.bollardName,
                        textPosition: 'bottom',
                        textVerticalAlign: 'middle',
                        textBackgroundColor: '#ffffff',
                    },
                    z: 5,
                });
                group.add(line);

            }
            //
            lastBerthWidth = berthInfo.bollardList[berthInfo.bollardList.length - 1].bollardDistance / self.opt.xAxis.scale;
        }
        group.position = obj.zeroPosition;
        this.baseGroup.add(group);
        this.zrender.add(this.baseGroup);
    }

    _drawY(data) {
        let array = data.times;
        obj.intervalMills = data.intervalMills;
        obj.yData = array;
        let group = new zrender.Group();
        let allLength = (array.length - 1) * self.opt.yAxis.interval;
        obj.yLength = allLength;
        let line = new zrender.Line({
            shape: {
                x1: 0,
                y1: 0,
                x2: 0,
                y2: -allLength,
            },
            style: {
                lineWidth: 3,
            }
        });
        group.add(line);
        // 刻度
        let yGroup = new zrender.Group();
        for (let i = 0; i < array.length; i++) {
            let obj = array[i];
            let width = 20;
            if (obj.level === 1) {
                width = 30;
            }
            let yOpts = {
                shape: {
                    x1: 0,
                    y1: -i * self.opt.yAxis.interval,
                    x2: -width,
                    y2: -i * self.opt.yAxis.interval,
                },
                style: {
                    textPosition: 'left',
                }
            };
            if (obj.show) {
                yOpts.style.text = obj.name;
            }
            let line = new zrender.Line(yOpts);
            yGroup.add(line);
        }
        group.add(yGroup);
        group.position = obj.zeroPosition;
        this.baseGroup.add(group);
    }

    /**
     * 中间坐标轴围成的画布以及添加ship的事件
     * @private
     */
    _drawCenter() {
        let group = new zrender.Group({ name: 'centerGroup' });
        obj.crossGroup = new zrender.Group({ name: 'crossGroup' });
        obj.preAddShipGroup = new zrender.Group({ name: 'preAddShipGroup' });
        obj.crossTextGroup = new zrender.Group({ name: 'crossTextGroup' });
        obj.leftTopPosition = [obj.zeroPosition[0], obj.zeroPosition[1] - obj.yLength];
        // 中心画布
        let rect = new zrender.Rect({
            name: 'centerRect',
            shape: {
                x: 0,
                y: -obj.yLength,
                width: obj.xLength,
                height: obj.yLength,
            },
            style: {
                fill: '#f7f7f7',
            }
        });
        group.add(rect);
        group.on('mousemove', function (event) {
            if (obj.crossGroup) {
                if (!obj.hdShipDragging) {
                    obj.curShipGroupId = '';
                }
                obj.crossGroup.removeAll();
                let xCross = self._getXCrossLine(event.offsetX);
                obj.crossGroup.add(xCross);
                let yCross = self._getYCrossLine(event.offsetY);
                obj.crossGroup.add(yCross);
            }
            self._drawPreAddShip(event);
            self._hideShipMouseOver();
        });
        $(window).on('mouseover', function () {
            self._hideShipMouseOver();
        });
        group.on('click', function (event) {
            if (self.opt.preAddOpt.flag) {
                // 获取xy轴坐标值
                let xScope = [];
                xScope[0] = self._getXDataByPos(event.offsetX);
                xScope[1] = self._getXDataByPos(event.offsetX + self.opt.preAddOpt.width);
                let yScope = [];
                yScope[0] = self._getYDataByPos(event.offsetY);
                yScope[1] = self._getYDataByPos(event.offsetY - self.opt.preAddOpt.height);
                if (xScope[0] && xScope[1] && yScope[0] && yScope[1]) {
                    self.opt.preAddOpt.flag = false;
                    obj.preAddShipGroup.removeAll();
                    obj.preAddShipGroup.dirty();
                    // 赋值ship
                    let ship = obj.preAddShipData;
                    ship.planBerthCode = xScope[0].berthCode;
                    ship.planBeginBollardCode = xScope[0].bollardCode;
                    ship.planBeginBollardName = xScope[0].bollardName;
                    ship.planEndBollardCode = xScope[1].bollardCode;
                    ship.planEndBollardName = xScope[1].bollardName;
                    ship.planBerthStartTimestamp = yScope[0].timestamp;
                    ship.etb = yScope[0].code;
                    ship.planBerthEndTimestamp = yScope[1].timestamp;
                    ship.etu = yScope[1].code;
                    self.addOneShip(ship, true);
                    if (typeof obj.preAddShipSaveHandler === 'function') {
                        obj.preAddShipSaveHandler(obj.preAddShipData, obj.preAddShipCallback);
                    }
                }
            }
        });
        // 右键取消
        group.on('contextmenu', function (event) {
            // 阻止默认事件
            event.event.preventDefault();
            if (self.opt.preAddOpt.flag) {
                self.opt.preAddOpt.flag = false;
                obj.preAddShipGroup.removeAll();
                obj.preAddShipGroup.dirty();
                if (typeof obj.preAddShipCallback === 'function') {
                    obj.preAddShipCallback(false);
                }
            }
        });
        group.position = obj.zeroPosition;
        this.baseGroup.add(group);
        this.baseGroup.add(obj.crossGroup);
        this.baseGroup.add(obj.crossTextGroup);
        this.baseGroup.add(obj.preAddShipGroup);
    }

    _drawPreAddShip(event) {
        if (self.opt.preAddOpt.flag) {
            if (obj.preAddShipGroup) {
                obj.preAddShipGroup.removeAll();
                // x y 偏移
                let offset = 0;
                // x y 文本
                let zlevel = 3;
                let xyData = self._getCurrentXYData();
                let width = self.opt.preAddOpt.width;
                let height = self.opt.preAddOpt.height;
                if (!obj.preAddShipData.sortNo) {
                    obj.preAddShipData.sortNo = '';
                }
                let berthShip = new BerthShip({
                    name: 'berthShip',
                    draggable: false,
                    shape: {
                        x: event.offsetX + offset,
                        y: event.offsetY - height - offset,
                        width: width,
                        height: height,
                        arrow: obj.preAddShipData.planBerthingMode,
                    },
                    style: {
                        fill: '#7fc3fc',
                        stroke: '#272cff',
                        opacity: 1,
                        shadowBlur: 5,
                        shadowColor: '#272cff',
                        text: '{no|' + (obj.preAddShipData.sortNo) + '}\n{info|' + obj.preAddShipData.shipCode + '/' + obj.preAddShipData.imVoyage + '/' + obj.preAddShipData.exVoyage + '}',
                        rich: {
                            no: {
                                fontSize: 25,
                                textFill: '#d90000',
                                textAlign: 'center',
                            },
                            info: {

                                fontSize: 16,
                                textFill: '#000',
                                textAlign: 'right',
                            }
                        }
                    },
                    zlevel,

                });
                obj.preAddShipGroup.add(berthShip);
                // 揽桩
                let beginCircle = new zrender.Circle({
                    name: 'beginCircle',
                    shape: {
                        cx: event.offsetX,
                        cy: event.offsetY - height / 2 - offset,
                        r: self.opt.shipOpt.circleR,
                    },
                    style: {
                        text: xyData.x.bollardName,
                        textBackgroundColor: '#fff',
                        textPosition: 'left',
                        fill: '#fff',
                        stroke: '#431eff',
                    },
                    zlevel,
                });
                obj.preAddShipGroup.add(beginCircle);
                // 时间文本
                let text = new zrender.Text({
                    style: {
                        text: xyData.y.name,
                        textBackgroundColor: '#fff',
                    },
                    zlevel,
                });
                let bound = text.getBoundingRect();
                text.position = [event.offsetX + (width - bound.width) / 2, event.offsetY + 2];
                obj.preAddShipGroup.add(text);
                // 不能妨碍其他鼠标事件
                obj.preAddShipGroup.silent = true;
            }
        }
    }

    _drawShips(array) {
        for (let i = 0; i < array.length; i++) {
            let ship = array[i];
            this.addOneShip(ship);
        }

    }

    _getCurrentXYData() {
        let retObj = null;
        let eles = obj.crossGroup.children();
        if (eles.length === 2) {
            retObj = {};
            for (let i = 0; i < eles.length; i++) {
                let ele = eles[i];
                retObj[ele.hdtype] = ele.hddata;
            }
        }
        return retObj;
    }

    _getYDataByPos(pos) {
        let offset = obj.zeroPosition[1] - pos;
        let index = Math.floor((offset - 0) / self.opt.yAxis.interval);
        return obj.yData[index];
    }

    _getYCrossLine(pos) {
        let offset = obj.zeroPosition[1] - pos;
        let index = Math.floor((offset - 0) / self.opt.yAxis.interval);
        let yCrossName = '';
        if (obj.yData[index]) {
            yCrossName = obj.yData[index].code.substr(5);
        }
        let yCross = new zrender.Line({
            shape: {
                x1: obj.leftTopPosition[0],
                y1: pos,
                x2: obj.leftTopPosition[0] + obj.xLength,
                y2: pos,
            },
            silent: true,
            style: {
                text: yCrossName,
                textPadding: 2,
                fontSize: 16,
                fontWeight: 'bold',
                textFill: '#ffffff',
                textPosition: 'left',
                lineDash: [2.5, 2],
                textBackgroundColor: '#555555',
            },
            zlevel: 1,
        });
        yCross.hddata = obj.yData[index];
        yCross.hdpos = pos;
        yCross.hdtype = 'y';
        yCross.hdindex = index;
        if (!yCrossName) {
            yCross.hdOverBorder = true;
        }
        return yCross;
    }

    _getXDataByPos(pos) {
        let index = self._getXDataIndexByPos(pos);
        return obj.xData[index];
    }

    _getXCrossLine(pos) {
        let index = self._getXDataIndexByPos(pos);
        let xCrossName = '';
        if (obj.xData[index]) {
            xCrossName = obj.xData[index].bollardName;
        }
        let xCross = new zrender.Line({
            shape: {
                x1: pos,
                y1: obj.leftTopPosition[1],
                x2: pos,
                y2: obj.yLength + obj.leftTopPosition[1],
            },
            silent: true,
            style: {
                text: xCrossName,
                textPadding: 2,
                fontSize: 16,
                fontWeight: 'bold',
                textFill: '#ffffff',
                textPosition: 'bottom',
                lineDash: [2.5, 2],
                textBackgroundColor: '#555555',
            },
            zlevel: 1,
        });
        xCross.hddata = obj.xData[index];
        xCross.hdpos = pos;
        xCross.hdtype = 'x';
        xCross.hdindex = index;
        if (!xCrossName) {
            xCross.hdOverBorder = true;
        }
        return xCross;
    }

    _getXDataIndexByPos(pos) {
        let offset = pos - obj.zeroPosition[0] - 1;
        if (offset < 0) {
            return -1;
        }
        let lengthMeter = offset * self.opt.xAxis.scale;
        let index;
        for (let i = 0; i < obj.xData.length; i++) {
            let ele = obj.xData[i];
            if (lengthMeter <= ele.bollardDistance) {
                index = i;
                break;
            }
        }
        return index;
    }

    _findYPositionByTime(timestamp) {
        let diff = timestamp - obj.yData[0].timestamp;
        let position = self.opt.yAxis.interval * diff / obj.intervalMills;
        position = obj.zeroPosition[1] - position;
        return position;
    }

    _findXPositionByCode(berthCode, bollardCode) {
        let reObj = {};
        let index = obj.xData.findIndex(function (ele) {
            // if (ele.berthCode === berthCode) {
            if (ele.bollardCode === bollardCode) {
                return true;
            }
            // }
            return false;
        });
        if (index !== -1) {
            reObj.index = index;
            reObj.pos = obj.xData[index].bollardDistance / self.opt.xAxis.scale + obj.zeroPosition[0];
        } else {
            reObj = false;
        }
        return reObj;
    }

    /**
     * 红色位置信息:实时更新显示最新位置
     * @param ship
     * @param event
     * @param target
     * @private
     */
    _shipCrossText(ship, event, target) {
        if (obj.crossTextGroup) {
            obj.crossTextGroup.removeAll();
            let bound = self._getBoundRectByCrossLines();
            let eles = obj.crossGroup.children();
            let opts = {
                style: {
                    fontSize: 16,
                    textFill: '#d90000',
                    textBackgroundColor: '#fff',
                },
                zlevel: 1,
            };
            for (let i = 0; i < eles.length; i++) {
                let ele = eles[i];
                if (ele.name === 'left') {
                    if (ele.hddata) {
                        opts.style.text = ele.hddata.bollardName;
                        let text = new zrender.Text(opts);
                        let textBound = text.getBoundingRect();
                        text.position = [bound.x - textBound.width - 20, bound.y + (bound.height - textBound.height) / 2];
                        obj.crossTextGroup.add(text);
                    }
                }
                else if (ele.name === 'right') {
                    if (ele.hddata) {
                        opts.style.text = ele.hddata.bollardName;
                        let text = new zrender.Text(opts);
                        let textBound = text.getBoundingRect();
                        text.position = [bound.x + bound.width + 20, bound.y + (bound.height - textBound.height) / 2];
                        obj.crossTextGroup.add(text);
                    }
                }
                else if (ele.name === 'bottom') {
                    if (ele.hddata) {
                        opts.style.text = ele.hddata.code;
                        let text = new zrender.Text(opts);
                        let textBound = text.getBoundingRect();
                        text.position = [bound.x + (bound.width - textBound.width) / 2, bound.y + bound.height + 20];
                        obj.crossTextGroup.add(text);

                    }
                }
                else if (ele.name === 'top') {
                    if (ele.hddata) {
                        opts.style.text = ele.hddata.code;
                        let text = new zrender.Text(opts);
                        let textBound = text.getBoundingRect();
                        text.position = [bound.x + (bound.width - textBound.width) / 2, bound.y - textBound.height - 20];
                        obj.crossTextGroup.add(text);
                    }
                }
            }
        }
    }

    _getBoundRectByCrossLines() {
        let eles = obj.crossGroup.children();
        let bound = {};
        for (let i = 0; i < eles.length; i++) {
            let ele = eles[i];
            bound[ele.name] = ele.hdpos;
        }
        bound.x = bound['left'];
        bound.width = bound['right'] - bound['left'];
        bound.y = bound['top'];
        bound.height = bound['bottom'] - bound['top'];
        return bound;
    }

    /**
     * 船图形位置信息：起点归0，重新赋值图形高度，topOffset: 上边框移动的距离
     * @param target
     * @param topOffset
     * @private
     */
    _freshShipPosition(target, topOffset) {

        let height = target.yScope[0] - target.yScope[1];
        let shipBox = target.childOfName('shipBox');
        shipBox.eachChild(function (ele) {
            ele.position = [0, 0];
        });
        let rect = shipBox.childOfName('berthShip');
        let textGroup = shipBox.childOfName('shipText');
        let borderGroup = shipBox.childOfName('border');
        let beginCircle = shipBox.childOfName('beginCircle');
        let endCircle = shipBox.childOfName('endCircle');
        if (topOffset || topOffset === 0) {
            // rect
            rect.attr('shape', {
                y: 0,
                height: height,
            });
            textGroup.eachChild(function (ele) {
                ele.invisible = false;
                if (ele.name === 'bottom') {
                    ele.position[1] = ele.position[1] + topOffset;
                } else if (ele.name !== 'top') {
                    ele.position[1] = ele.position[1] + topOffset / 2;
                }

            });
            borderGroup.eachChild(function (ele) {
                ele.position = [0, 0];
            });
            beginCircle.attr('shape', {
                cy: height / 2,
            });
            endCircle.attr('shape', {
                cy: height / 2,
            });

        }
        target.position = [target.xScope[0], target.yScope[1]];
        target.dirty();
    }

    /**
     * 更新船数据和文本
     * @param ship
     * @param target
     * @private
     */
    _updateShipXY(ship, target) {
        if (obj.crossGroup) {
            let eles = obj.crossGroup.children();
            let berthCode = [];
            // 4条辅助线
            if (eles.length === 4) {
                for (let i = 0; i < eles.length; i++) {
                    let ele = eles[i];
                    if (ele.name === 'left') {
                        // ele.hddata
                        if (ele.hddata) {
                            ship.planBeginBollardCode = ele.hddata.bollardCode;
                            ship.planBeginBollardName = ele.hddata.bollardName;
                            berthCode[0] = ele.hddata.berthCode;
                            target.xScope[0] = ele.hdpos;
                        }
                    }
                    else if (ele.name === 'right') {
                        // ele.hddata
                        if (ele.hddata) {
                            ship.planEndBollardCode = ele.hddata.bollardCode;
                            ship.planEndBollardName = ele.hddata.bollardName;
                            berthCode[1] = ele.hddata.berthCode;
                            target.xScope[1] = ele.hdpos;
                        }
                    }
                    else if (ele.name === 'bottom') {
                        // ele.hddata
                        if (ele.hddata) {
                            ship.planBerthStartTimestamp = ele.hddata.timestamp;
                            ship.etb = ele.hddata.code;
                            target.yScope[0] = ele.hdpos;
                        }
                    }
                    else if (ele.name === 'top') {
                        // ele.hddata
                        if (ele.hddata) {
                            ship.planBerthEndTimestamp = ele.hddata.timestamp;
                            ship.etu = ele.hddata.code;
                            target.yScope[1] = ele.hdpos;
                        }
                    }
                }
                // 如果泊位已编辑且前后不一致，则不赋值.反之赋值
                if (!(ship.planBerthCodeEditFlag && berthCode[0] !== berthCode[1])) {
                    ship.planBerthCode = berthCode[0];
                    ship.planBerthCodeEditFlag = false;
                }
            }
            // 更新 ship上的text
            let shipBoxGroup = target.childOfName('shipBox');
            let textGroup = shipBoxGroup.childOfName('shipText');
            textGroup.eachChild(function (ele) {
                if (ele.name === 'top') {
                    ele.attr('style', {
                        text: ship.etu
                    });
                } else if (ele.name === 'bottom') {
                    ele.attr('style', {
                        text: ship.etb
                    });
                } else if (ele.name === 'left') {
                    ele.attr('style', {
                        text: ship.planBeginBollardName
                    });
                } else if (ele.name === 'right') {
                    ele.attr('style', {
                        text: ship.planEndBollardName
                    });
                }
            });


        }
    }

    /**
     * 4条辅助线
     * @param ship
     * @param event
     * @param target
     * @private
     */
    _shipCrossLines(ship, event, target) {
        if (obj.crossGroup) {
            obj.curShipGroupId = target.id;
            obj.crossGroup.removeAll();
            let xCross = self._getXCrossLine(target.xScope[0] + event.target.position[0]);
            xCross.name = 'left';
            obj.crossGroup.add(xCross);
            let xCrossRight = self._getXCrossLine(target.xScope[1] + event.target.position[0]);
            xCrossRight.name = 'right';
            obj.crossGroup.add(xCrossRight);
            let yCross = self._getYCrossLine(target.yScope[0] + event.target.position[1]);
            yCross.name = 'bottom';
            obj.crossGroup.add(yCross);
            let yCrossTop = self._getYCrossLine(target.yScope[1] + event.target.position[1]);
            yCrossTop.name = 'top';
            obj.crossGroup.add(yCrossTop);
        }

    }

    _drawShipText(ship, width, height) {
        let group = new zrender.Group({
            name: 'shipText'
        });
        let shipHeight = height;
        let y = height - shipHeight;
        let opts = {
            style: {
                textBackgroundColor: '#fff',
            },
            z: 10,
        };
        opts.name = 'top';
        opts.style.text = ship.etu;
        let text = new zrender.Text(opts);
        let bound = text.getBoundingRect();
        text.position = [(width - bound.width) / 2, y - bound.height - 5];
        group.add(text);
        opts.name = 'bottom';
        opts.style.text = ship.etb;
        text = new zrender.Text(opts);
        bound = text.getBoundingRect();
        text.position = [(width - bound.width) / 2, height + 2];
        group.add(text);
        opts.name = 'left';
        opts.style.text = ship.planBeginBollardName;
        text = new zrender.Text(opts);
        bound = text.getBoundingRect();
        text.position = [-5 - bound.width, y + (shipHeight - bound.height) / 2];
        group.add(text);
        opts.name = 'right';
        opts.style.text = ship.planEndBollardName;
        text = new zrender.Text(opts);
        bound = text.getBoundingRect();
        text.position = [width + 5, y + (shipHeight - bound.height) / 2];
        group.add(text);
        group.silent = true;
        return group;
    }

    /**
     * 拖拽实现：上边框
     * @param ship
     * @param target
     * @returns {zrender.Group}
     * @private
     */
    _drawChangeBorder(ship, target) {
        // 计算可拖拽的边框宽度，（高度暂时不用）
        let height = target.yScope[0] - target.yScope[1];
        let width = target.xScope[1] - target.xScope[0];
        let group = new zrender.Group({
            name: 'border'
        });
        // 可拖拽的隐藏长条
        let lineTop = new zrender.Line({
            name: 'lineTop',
            invisible: true,
            draggable: true,
            shape: {
                x1: 0,
                y1: 0,
                x2: width,
                y2: 0,
            },
            style: {
                lineWidth: 5,
            },
            z: 20,
            cursor: 'n-resize',
        });
        // 边框移动过程 
        lineTop.on('drag', function (event) {
            event.cancelBubble = true;
            let height = target.yScope[0] - target.yScope[1];
            // 边界判断：上不能超过y轴边界，下不能超过一个最小高度（更不能反向穿过船体）
            let maxPos = obj.zeroPosition[1] - obj.yLength >= event.target.position[1] + target.position[1];
            let minPos = event.target.position[1] >= height - self.opt.shipOpt.height;
            if (!(maxPos || minPos)) {
                // 改变shipRect
                // 根据新的位置改变其他图形的位置：主要是y 和height
                let shipBox = target.childOfName('shipBox');
                let rect = shipBox.childOfName('berthShip');
                rect.attr('shape', {
                    y: event.target.position[1],
                    height: height - event.target.position[1],
                });
                let beginCircle = shipBox.childOfName('beginCircle');
                beginCircle.attr('shape', {
                    cy: (height + event.target.position[1]) / 2
                });
                let endCircle = shipBox.childOfName('endCircle');
                endCircle.attr('shape', {
                    cy: (height + event.target.position[1]) / 2
                });
                let textGroup = shipBox.childOfName('shipText');
                textGroup.eachChild(function (ele) {
                    if (ele.name !== 'bottom') {
                        ele.invisible = true;
                    }

                });
            }
            // 穿过船体即 minPos处理：显示坐标辅助线
            if (minPos) {
                obj.crossGroup.removeAll();
                let xCross = self._getXCrossLine(event.offsetX);
                obj.crossGroup.add(xCross);
                let yCross = self._getYCrossLine(target.position[1] + height - self.opt.shipOpt.height);
                obj.crossGroup.add(yCross);
            }

        });
        lineTop.on('mousemove', function (event) {
            event.cancelBubble = true;
        });
        // 判断是否进行边框拖拽
        lineTop.on('mousedown', function (event) {
            if (event.which === 1) {
                // 屏蔽船体拖拽，只响应边框拖拽
                obj.hdLineDragging = true;
                obj.hdShipDragging = false;
                // 保存当前拖拽的边框id，zrender自动生成
                obj.curBorderId = this.id;
                lineTop.draggable = true;
            } else {
                obj.hdLineDragging = false;
                obj.hdShipDragging = false;
                obj.curBorderId = '';
                lineTop.draggable = false;
            }
        });
        // 绑定window，使鼠标脱离边框本身，在任意位置释放，都能正确响应mouseup事件
        $(window).on('mouseup', { lineTop }, function (event) {
            if (obj.hdLineDragging && obj.curBorderId === event.data.lineTop.id) {
                event.data.lineTop.trigger('mouseup');
            }
            if (!obj.curBorderId) {
                obj.hdLineDragging = false;
            }
        });
        lineTop.on('mouseup', function (event) {
            if (obj.crossGroup && obj.hdLineDragging && obj.curBorderId === this.id) {
                obj.hdLineDragging = false;
                obj.hdShipDragging = false;
                obj.curShipGroupId = '';
                this.position = [0, 0];
                obj.curBorderId = '';
                // 根据辅助线计算位置
                let childCount = obj.crossGroup.childCount();
                if (childCount > 0) {
                    let overBorder = false;
                    let yOffset = 0;
                    let changeEle = {};// 新的y轴坐标信息
                    obj.crossGroup.eachChild(function (ele) {
                        // 超出横纵坐标
                        if (ele.hdOverBorder) {
                            overBorder = true;
                            return false;
                        } else if (ele.hdtype === 'y' && ele.hddata) {
                            // 辅助线可能是4条
                            if (childCount > 2) {
                                if (ele.name === 'top') {
                                    changeEle = ele;
                                }
                            } else {
                                changeEle = ele;
                            }

                        }
                    });
                    if (changeEle) {
                        yOffset = changeEle.hdpos;
                    }
                    if (overBorder || yOffset + self.opt.shipOpt.height > target.yScope[0]) {
                        // 超出边界恢复上次位置
                        self._freshShipPosition(target, 0);
                        self._updateShipXY(ship, target);
                    } else {
                        if (changeEle) {
                            // 移动的距离
                            let offset = target.yScope[1] - changeEle.hdpos;
                            // 根据新的坐标数据，更新船数据
                            ship.planBerthEndTimestamp = changeEle.hddata.timestamp;
                            ship.etu = changeEle.hddata.code;
                            target.yScope[1] = changeEle.hdpos;
                            self._freshShipPosition(target, offset);
                            self._updateShipXY(ship, target);
                            if (typeof self.opt.shipSaveHandler === 'function') {
                                self.opt.shipSaveHandler(ship);
                            }
                        }
                    }

                }
            }
        });
        group.add(lineTop);
        return group;
    }

    _resizeXPosByShipLength(xObj, ship) {
        while (xObj[1].pos - xObj[0].pos < ship.shipLength / self.opt.xAxis.scale) {
            // 判断 在前还是后增加
            let newIndex = -1;
            let _indx = 0;
            if (obj.xData[xObj[1].index + 1]) {
                newIndex = xObj[1].index + 1;
                _indx = 1;
            } else if (obj.xData[xObj[0].index - 1]) {
                newIndex = xObj[0].index - 1;
                _indx = 0;
            } else {
                break;
            }
            if (newIndex !== -1) {
                let reObj = {};
                reObj.index = newIndex;
                reObj.pos = obj.xData[newIndex].bollardDistance / self.opt.xAxis.scale + obj.zeroPosition[0];
                xObj[_indx] = reObj;
            }

        }


    }
}

export default ShipBerth;
