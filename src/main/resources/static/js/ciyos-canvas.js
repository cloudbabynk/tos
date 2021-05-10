/**
 * @author matt
 * @date  2016-03-01
 */


/**
 * JS补零操作
 *+---------------------------------
 * @param data  str    数值
 * @param string n   长度
 *+---------------------------------
 * @return void
 */
  function str_pad(str, n) {
    var len = str.toString().length;
    while (len < n) {
      str = "0" + str;
        len++;
    }
    return str;
  }


/**
 * 判断loc点是否在左上角点（x0，y0）,右下角点（x1，y1）的范围内
 */
locInRect = function(loc, x0, y0, x1, y1) {
  if(loc.x >= x0 && loc.y >= y0 && loc.x <= x1 && loc.y <= y1) {
    return true;//在范围内
  } else{
    return false;//不在范围内
  }
}
/**
 * 判断x1y1, x2y2点的大小，返回新的起始点（左上角点）和结束点（右下角点）
 * @param x1y1
 * @param x2y2
 * @returns {}
 */
resetStartEndXY = function(x1y1, x2y2) {
  var startendPoint={};
  //向右拖拽
  if(x1y1.x<=x2y2.x){
    //拖拽方向：右下
    if(x1y1.y<=x2y2.y){
      //起点坐标:所在场位的起点坐标
      startendPoint.start=new XY(x1y1.x,x1y1.y);
      startendPoint.end=new XY(x2y2.x,x2y2.y);
    }else{
      //拖拽方向：右上
      //起点坐标:所在场位的左下角坐标
      startendPoint.start=new XY(x1y1.x,x2y2.y);
      startendPoint.end=new XY(x2y2.x,x1y1.y);
    }
  }else{//向左拖拽 
    if(x1y1.y<=x2y2.y){
      //拖拽方向：左下
      //起点坐标：所在场位的右上角坐标
      startendPoint.start=new XY(x2y2.x,x1y1.y);
      startendPoint.end=new XY(x1y1.x,x2y2.y);
    }else{
      //拖拽方向：左上
      //起点坐标：所在场位的右下角坐标
      startendPoint.start=new XY(x2y2.x,x2y2.y);
      startendPoint.end=new XY(x1y1.x,x1y1.y);
    }
  }
  return startendPoint;
}
/**
 * 判断矩形的四个点是否都在同一个矩形内，或者都不在矩形内
 * @param startXY左上角点坐标对象
 * @param endXY右下角点坐标对象
 * @param list矩形集合
 * @returns {___anonymous1166_1167}
 */

newRectIsInOldRect = function(startXY, endXY, list) {
  var object={};
  var flag=false;
  var x0, y0, x1, y1;
  var startRect=null;//新矩形起始点所在的矩形
  var endRect=null;//新矩形结束点所在的矩形
  var zxRect=null;//新矩形左下角的点所在的矩形
  var ysRect=null;//新矩形右上角的点所在的矩形
  for(var i = 0; i < list.length; i++) {
    x0 = list[i].startX;
    y0 = list[i].startY;
    x1 = x0 + list[i].width;
    y1 = y0 + list[i].height;
    if(locInRect(startXY, x0, y0, x1, y1)) {
      startRect=list[i];
      break;
    }
  }
  for(var j = 0; j < list.length; j++) {
    x0 = list[j].startX;
    y0 = list[j].startY;
    x1 = x0 + list[j].width;
    y1 = y0 + list[j].height;
    if(locInRect(endXY, x0, y0, x1, y1)) {
      endRect=list[j];
      break;
    }
  }
  for(var k = 0; k < list.length; k++) {
    x0 = list[k].startX;
    y0 = list[k].startY;
    x1 = x0 + list[k].width;
    y1 = y0 + list[k].height;
    if(locInRect(new XY(startXY.x,endXY.y), x0, y0, x1, y1)) {
      zxRect=list[k];
      break;
    }
  }
  for(var h = 0; h < list.length; h++) {
    x0 = list[h].startX;
    y0 = list[h].startY;
    x1 = x0 + list[h].width;
    y1 = y0 + list[h].height;
    if(locInRect(new XY(endXY.x,startXY.y), x0, y0, x1, y1)) {
      ysRect=list[h];
      break;
    }
  }
  if(startRect == null && endRect == null && zxRect == null && ysRect == null) {
    flag=true;
  } else if(startRect != null && endRect != null 
      && zxRect != null && ysRect != null 
      && startRect.placeId === endRect.placeId 
      && endRect.placeId === zxRect.placeId 
      && zxRect.placeId === ysRect.placeId
      && ysRect.placeId === startRect.placeId) {
    flag=true;
  }
  object.flag = flag;
  object.data = startRect;
  return object;
}


//绘制箭头需要的线，参数分别为，CanvasLayer，直线的起始坐标，中间坐标，结束坐标，上箭头坐标，下箭头坐标
function drawTwoLine(canvas,x0y0,x1y1,x2y2,jx0jy0,jx1jy1,color,size){
  var line1 = new Line(x0y0, x1y1, size, color);
  var line2 = new Line(x1y1, x2y2, size, color);
  var line3 = new Line(jx0jy0, x2y2, size, color);
  var line4 = new Line(x2y2, jx1jy1, size, color);
  var linearr=[];
  var gridarr=[];
  linearr.push(line1);
  linearr.push(line2);
  linearr.push(line3);
  linearr.push(line4);
  var grid=new Grid(linearr);
  gridarr.push(grid);
  canvas.draw(gridarr);
}
/**
 * 绘制箭头,startXY绘制起始点（鼠标按下点）, endXY, startBay, endBay
 * @param canvas
 * @param startXY绘制起始点（鼠标按下点）
 * @param endXY绘制结束点（鼠标抬起点）
 * @param startBay绘制矩形的逻辑起始点（矩形左上角点）
 * @param endBay绘制矩形的逻辑结束点（矩形右下角点）
 * @param color
 * @param size
 * @param vertical判断箭头的中间转折点与起始点是否在同一个垂直线上，在同一垂直线上为true否则false
 */
drawArrow = function(canvas, startXY, endXY, startBay, endBay,color,size,vertical) {
  if(startBay!= undefined && endBay!= undefined){
    var x0y0,x1y1;//线条起始坐标和结束坐标
    if(startXY.x <= endXY.x) {
      if(startXY.y <= endXY.y) {
        if(vertical) {
          drawTwoLine(canvas,new XY(startBay.x+4,startBay.y+4),new XY(endBay.x-4,startBay.y+4),new XY(endBay.x-4,endBay.y-4),new XY(endBay.x-8,endBay.y-8),new XY(endBay.x,endBay.y-8),color,size);
        } else {
          drawTwoLine(canvas,new XY(startBay.x+4,startBay.y+4),new XY(startBay.x+4,endBay.y-4),new XY(endBay.x-4,endBay.y-4),new XY(endBay.x-8,endBay.y-8),new XY(endBay.x-8,endBay.y),color,size);
        }
      }else{
        if(vertical) {
          drawTwoLine(canvas,new XY(startBay.x+4,endBay.y-4),new XY(endBay.x-4,endBay.y-4),new XY(endBay.x-4,startBay.y+4),new XY(endBay.x-8,startBay.y+8),new XY(endBay.x,startBay.y+8),color,size);
        } else {
          drawTwoLine(canvas,new XY(startBay.x+4,endBay.y-4),new XY(startBay.x+4,startBay.y+4),new XY(endBay.x-4,startBay.y+4),new XY(endBay.x-8,startBay.y+8),new XY(endBay.x-8,startBay.y),color,size);
        }
      }
    }else{
      if(startXY.y <= endXY.y) {
        if(vertical) {
          drawTwoLine(canvas,new XY(endBay.x-4,startBay.y+4),new XY(startBay.x+4,startBay.y+4),new XY(startBay.x+4,endBay.y-4),new XY(startBay.x,endBay.y-8),new XY(startBay.x+8,endBay.y-8),color,size);
        } else {
          drawTwoLine(canvas,new XY(endBay.x-4,startBay.y+4),new XY(endBay.x-4,endBay.y-4),new XY(startBay.x+4,endBay.y-4),new XY(startBay.x+8,endBay.y),new XY(startBay.x+8,endBay.y-8),color,size);
        }
      }else{
        if(vertical) {
          drawTwoLine(canvas,new XY(endBay.x-4,endBay.y-4),new XY(startBay.x+4,endBay.y-4),new XY(startBay.x+4,startBay.y+4),new XY(startBay.x,startBay.y+8),new XY(startBay.x+8,startBay.y+8),color,size);
        } else {
          drawTwoLine(canvas,new XY(endBay.x-4,endBay.y-4),new XY(endBay.x-4,startBay.y+4),new XY(startBay.x+4,startBay.y+4),new XY(startBay.x+8,startBay.y),new XY(startBay.x+8,startBay.y+8),color,size);
        }
      }
    }
  }
}

/**
 * 坐标
 * @param {type} x横坐标
 * @param {type} y纵坐标
 * @returns {XY}
 */
function XY(x, y) {
    this.x = x;
    this.y = y;
    this.setX = function (x) {
        this.x = x;
    }
    this.getX = function () {
        return this.x;
    }
    this.setY = function (y) {
        this.y = y;
    }
    this.getY = function () {
        return this.y;
    }
}

/**
 * 线段
 * @param {XY} x1y1起点坐标
 * @param {XY} x2y2终点坐标
 * @param {type} size线的宽度
 * @param {type} color颜色
 * @returns {Line}
 */
function Line(x1y1, x2y2, size, color) {
    this.x1y1 = x1y1;
    this.x2y2 = x2y2;
    this.size = size;
    this.color = color;
    this.setX1y1 = function (x1y1) {
        this.x1y1 = x1y1;
    }
    this.setX2y2 = function (x2y2) {
        this.x2y2 = x2y2;
    }
    this.setSize = function (size) {
        this.size = size;
    }
    this.setColor = function (color) {
        this.color = color
    }
    /**
     * 画线的方法
     * @param {type} canvasContext 
     */
    this.draw = function (canvasContext) {
        canvasContext.strokeStyle = this.color;
        canvasContext.lineWidth = this.size;
        canvasContext.beginPath();
        canvasContext.moveTo(x1y1.x, x1y1.y);
        canvasContext.lineTo(x2y2.x, x2y2.y);
        canvasContext.closePath();
        canvasContext.stroke();
    }
}
/**
 * 网格
 * @param {Line} lines线
 * @returns {Grid}
 */
function Grid(lines,color) {
    this.lines = lines;
    this.color= color;
    this.setLines = function (lines) {
        this.lines = lines;
    }
    /**
     * 画网格调用画线的draw
     * @param {type} canvasContext
     */
    this.draw = function (canvasContext) {
        for (var i = 0; i < this.lines.length; i++) {
            var line = lines[i];
            line.draw(canvasContext);
        }
    }
}

/**
 * 矩形边框
 * @param {XY} xy 起点坐标
 * @param {type} width 宽
 * @param {type} height 高
 * @param {type} color颜色
 * @returns {StrokeRec}
 */
function  StrokeRec(xy, width, height, color,lineWidth) {
    this.xy = xy;
    this.width = width;
    this.height = height;
    this.color = color;
    this.lineWidth=lineWidth;
    this.setXy = function (xy) {
        this.xy = xy;
    }
    this.setWidth = function (width) {
        this.width = width;
    }
    this.setHeight = function (height) {
        this.height = height;
    }
    this.setColor = function (color) {
        this.color = color;
    }
    /**
     * 画矩形框
     * @param {type} canvasContext
     */
    this.draw = function (canvasContext) {
        canvasContext.strokeStyle = this.color;
        canvasContext.lineWidth = this.lineWidth;
        canvasContext.strokeRect(this.xy.x, this.xy.y, this.width, this.height);//绘制矩形轮廓
    }
}
/**
 * 矩形块
 * @param {XY} xy 起点坐标
 * @param {type} width宽度
 * @param {type} height高度
 * @param {type} color颜色
 * @param {type} globalAlpha透明度
 * @returns {FillRec}
 */
function FillRec(xy, width, height, color,globalAlpha) {
    this.xy = xy;
    this.width = width;
    this.height = height;
    this.color = color;
    this.globalAlpha=globalAlpha!=null?globalAlpha:1;
    this.setXy = function (xy) {
        this.xy = xy;
    }
    this.setWidth = function (width) {
        this.width = width;
    }
    this.setHeight = function (height) {
        this.height = height;
    }
    this.setColor = function (color) {
        this.color = color;
    }
    this.draw = function (canvasContext) {
        canvasContext.fillStyle = this.color;
        canvasContext.globalAlpha=this.globalAlpha;
        canvasContext.fillRect(this.xy.x , this.xy.y , this.width, this.height);//绘制矩形块
    }
}

/**
 * 多边形边框
 * @param {xyArr} 多边形点的集合数组,其中的坐标元素为 new XY()对象
 * @param {type} color颜色
 * @returns {StrokePolygon}
 */
function StrokePolygon(xyArr, color) {
    this.xyArr = xyArr;
    this.color = color;
    this.setXyArr = function (xyArr) {
        this.xyArr = xyArr;
    }
    this.setColor = function (color) {
        this.color = color
    }
    /**
     * 画多边形边框的方法
     * @param {type} canvasContext 
     */
    this.draw = function (canvasContext) {        
        canvasContext.beginPath();
        canvasContext.moveTo(xyArr[0].x,xyArr[0].y);
        for (var i = 1; i < xyArr.length; i++) {
          canvasContext.lineTo(xyArr[i].x,xyArr[i].y);
        }
        canvasContext.closePath();
        canvasContext.strokeStyle = this.color;
        canvasContext.stroke();
    }
}
/**
 * 多边形块
 * @param {xyArr} 多边形点的集合数组
 * @param {type} color颜色
 * @returns {StrokePolygon}
 */
function FillPolygon(xyArr, color, globalAlpha) {
    this.xyArr = xyArr;
    this.color = color;
    this.globalAlpha=globalAlpha!=null?globalAlpha:1;
    
    this.setXyArr = function (xyArr) {
        this.xyArr = xyArr;
    }
    this.setColor = function (color) {
        this.color = color
    }
    /**
     * 画多边形块的方法
     * @param {type} canvasContext 
     */
    this.draw = function (canvasContext) {        
        canvasContext.beginPath();
        canvasContext.moveTo(xyArr[0].x,xyArr[0].y);
        for (var i = 1; i < xyArr.length; i++) {
          canvasContext.lineTo(xyArr[i].x,xyArr[i].y);
        }
        canvasContext.closePath();
        canvasContext.fillStyle = this.color;
        canvasContext.globalAlpha=this.globalAlpha;
        canvasContext.fill();
    }
}

function drawImage(img, xy) {
    this.img = img;
    this.xy = xy;
    
    this.setXy = function (xy) {
        this.xy = xy;
    }
    this.setImg = function (img) {
        this.img = img
    }
    /**
     * 画多边形块的方法
     * @param {type} canvasContext 
     */
    this.draw = function (canvasContext) {        
        canvasContext.drawImage(img, xy.x, xy.y);
    }
}

/**
 * 文本
 * @param {XY} xy 坐标
 * @param {type} text 要绘制的文本
 * @param {type} color 颜色
 * @returns {Text}
 */
function Text(xy, text, color) {
    this.xy = xy;
    this.text = text;
    this.color = color;
    this.setXy = function (xy) {
        this.xy = xy;
    }
    this.setText = function (text) {
        this.text = text;
    }
    this.setColor = function (color) {
        this.color = color;
    }
    /**
     * 绘制文本
     * @param {type} canvasContext
     */
    this.draw = function (canvasContext) {
        canvasContext.fillStyle = this.color;
        canvasContext.fillText(this.text, this.xy.x, this.xy.y)
    }
}
/**
 * 场位 
 * @param {type} x0 起点横坐标
 * @param {type} y0 起点纵坐标
 * @returns {Place}
 */
function Place(x0,y0){
    this.x0=x0;
    this.y0=y0;
    this.width=bayWidth;
    this.height=rowHeight;
}
/**
 * 贝位
 * @param {type} bayNo贝号
 * @param {type} x0 起点横坐标
 * @param {type} y0起点纵坐标
 * @param {type} rowNum排数
 * @param {type} rowSeq排序：0上->下，1下->上,
 * @returns {Bay}
 */
function Bay(bayNo,x0,y0,rowNum,rowSeq){
    this.bayNo=bayNo;
    this.x0=x0;
    this.y0=y0;
    this.width=bayWidth;
    this.height=rowNum*rowHeight;
    this.rowNum=rowNum;
    this.rowSeq=rowSeq;//排序：0上->下，1下->上,
    this.places=[];
    /*初始化函数，创建时自动调用.用于创建贝时，直接创建排。*/
    this.init=function(){
        for(var i=0;i<this.rowNum;i++){
            if(this.rowSeqNo=='0'){
                this.places[i]=new Place(this.x0,this.y0+i*rowHeight);  
            }else{
                this.places[rowNum-i-1]=new Place(this.x0,this.y0+i*rowHeight); 
            }
            
        }
    }
    //调用
    this.init();
}
/**
 * 场
 * @param {type} areaId 场ID
 * @param {type} areaNo 场号
 * @param {type} x0 起点横坐标
 * @param {type} y0 起点纵坐标
 * @param {type} bayNum 贝数
 * @param {type} rowNum 排数
 * @param {type} tierNum 层数
 * @param {type} schemeId 模式
 * @param {type} baySeq 贝序：0左->右，1右->左
 * @param {type} rowSeq 排序：0上->下，1下->上
 * @param {type} bayWidth 贝的宽度
 * @param {type} rowHeight 排的高度
 * @returns {Area}
 */
function Area(areaId, areaNo, x0, y0, bayNum, rowNum, tierNum, schemeId, baySeq, rowSeq, bayWidth1, rowHeight1) {
    this.areaId = areaId;//场区ID
    this.areaNo = areaNo;//场区号
    this.x0 = x0;//起始横坐标
    this.y0 = y0;// 起始纵坐标
    this.bayNum = bayNum;//贝数                                         
    this.rowNum = rowNum;//排数
    this.tierNum = tierNum;//最大层数
    this.schemeId = schemeId;//模式
    this.baySeq = baySeq;//贝序：0左->右，1右->左
    this.rowSeq = rowSeq;//排序：0上->下，1下->上,
    this.bays=null;//这个场的贝
    this.bayWidth = (bayWidth1==null)?bayWidth:bayWidth1;
    this.rowHeight = (rowHeight1==null)?rowHeight:rowHeight1;
    this.setBays=function(bays){
        this.bays=bays;
    }
    /**
     * 绘制场
     * @param {type} canvasContext
     */
    this.draw = function (canvasContext) {
        /*绘制场边框*/
        canvasContext.fillStyle = "#000000";
        canvasContext.fillText(this.areaNo, this.x0 - 20, this.y0 - 4);
        canvasContext.strokeStyle = "#000000";
        canvasContext.lineWidth = 1;
        var strokeRec = new StrokeRec(new XY(this.x0 - 0.5, this.y0 - 0.5), this.bayWidth * this.bayNum, this.rowHeight * this.rowNum, "#000000");
        strokeRec.draw(canvasContext);
        /*普通场区*/
        if (this.schemeId != "-1") {
            //画贝号
            for (var i = 0; i < this.bayNum; i++) {
                var x="";
                x = this.x0 + (i + 1) * this.bayWidth;
//                if(this.baySeq == '0'){//贝序：0左->右，1右->左
//                    x = this.x0 + (i + 1) * this.bayWidth;
//                }else{
//                    x = this.x0 + (this.bayNum - i) * this.bayWidth;
//                }
                var str = i + 1;
                /*if (str < 10) {
                    str = '00' + str;
                } else if (str >= 10 && str < 100) {
                    str = '0' + str;
                }*/
//                str=str_pad(str,bayNoWidth);
                str=str_pad(str,2);
                var text = new Text(new XY(x - this.bayWidth * 0.75, this.y0 - 7), str, "#0000ff");
//                text.draw(canvasContext);
            }
            //画排号
            for (var i = 0; i < this.rowNum; i++) {
                var y="";
                y = this.y0 + (i + 1) * this.rowHeight;
//                if (this.rowSeq == '0') {//排序：0上->下，1下->上,
//                    y = this.y0 + (i + 1) * this.rowHeight;
//                }else{
//                    y = this.y0 + (this.rowNum - i) * this.rowHeight;
//                }
                var str = i + 1;
                /*if (str < 10) {
                    str = '0' + str;
                }*/
//                str=str_pad(str,rowNoWidth);
                str=str_pad(str,2);
                var text = new Text(new XY(this.x0 - 15, y - this.rowHeight * 0.5), str, "#0000ff");
                text.draw(canvasContext);
            }
        } else {
            /*特殊场区*/
            var x1y1 = new XY(x0, y0);
            var x2y2 = new XY(x0 + this.bayNum * this.bayWidth, y0);
            var x3y3 = new XY(x0 + this.bayNum * this.bayWidth, y0 + this.rowNum * this.rowHeight);
            var x4y4 = new XY(x0, y0 + this.rowNum * this.rowHeight);
            var line1 = new Line(x1y1, x3y3, 1, "#000000");
            line1.draw(canvasContext);
            var line2 = new Line(x4y4, x2y2, 1, "#000000");
            line2.draw(canvasContext);
        }
    }
}
/**
 * 场
 * @param {type} areaId 场ID
 * @param {type} areaNo 场号
 * @param {type} x0 起点横坐标
 * @param {type} y0 起点纵坐标
 * @param {type} bayNum 贝数
 * @param {type} rowNum 排数
 * @param {type} tierNum 层数
 * @param {type} schemeId 模式
 * @param {type} baySeq 贝序：0左->右，1右->左
 * @param {type} rowSeq 排序：0上->下，1下->上
 * @param {type} bayWidth 贝的宽度
 * @param {type} rowHeight 排的高度
 * @returns {Area}
 */
function AreaPercent(areaId, areaNo, x0, y0, bayNum, rowNum, tierNum, schemeId, baySeq, rowSeq, bayWidth1, rowHeight1,zyl) {
    this.areaId = areaId;//场区ID
    this.areaNo = areaNo;//场区号
    this.x0 = x0;//起始横坐标
    this.y0 = y0;// 起始纵坐标
    this.bayNum = bayNum;//贝数                                         
    this.rowNum = rowNum;//排数
    this.tierNum = tierNum;//最大层数
    this.schemeId = schemeId;//模式
    this.baySeq = baySeq;//贝序：0左->右，1右->左
    this.rowSeq = rowSeq;//排序：0上->下，1下->上,
    this.bays=null;//这个场的贝
    this.bayWidth = (bayWidth1==null)?bayWidth:bayWidth1;
    this.rowHeight = (rowHeight1==null)?rowHeight:rowHeight1;
    this.setBays=function(bays){
        this.bays=bays;
    }
    var zylPercent = (Math.round(zyl * 10000)/100).toFixed(1) + '%';
    /**
     * 绘制场
     * @param {type} canvasContext
     */
    this.draw = function (canvasContext) {
        /*绘制场边框*/
        canvasContext.fillStyle = "#0000ff";
        canvasContext.fillText(this.areaNo, this.x0 - 20, this.y0 - 4);
        canvasContext.strokeStyle = "#0000ff";
        canvasContext.lineWidth = 1;
        if(zyl != 0){
        	canvasContext.fillText(zylPercent,this.x0 + this.bayWidth * this.bayNum * 0.5,this.y0 - 4);
        }else{
        	canvasContext.fillText(zylPercent,this.x0 + 20,this.y0 - 4);
        }
        
        if(zyl >= 1){
        	var fillrect = new FillRec(new XY(this.x0 - 0.5, this.y0 - 0.5), this.bayWidth * this.bayNum, this.rowHeight * this.rowNum, "#0000ff","1");
        }else{
        	var fillrect = new FillRec(new XY(this.x0 - 0.5, this.y0 - 0.5), this.bayWidth * this.bayNum * zyl, this.rowHeight * this.rowNum, "#0000ff","1");
        }
        
        fillrect.draw(canvasContext);
        /*普通场区*/
        if (this.schemeId != "-1") {
            //画贝号
            for (var i = 0; i < this.bayNum; i++) {
                var x="";
                x = this.x0 + (i + 1) * this.bayWidth;
//                if(this.baySeq == '0'){//贝序：0左->右，1右->左
//                    x = this.x0 + (i + 1) * this.bayWidth;
//                }else{
//                    x = this.x0 + (this.bayNum - i) * this.bayWidth;
//                }
                var str = i + 1;
                /*if (str < 10) {
                    str = '00' + str;
                } else if (str >= 10 && str < 100) {
                    str = '0' + str;
                }*/
//                str=str_pad(str,bayNoWidth);
                str=str_pad(str,2);
                var text = new Text(new XY(x - this.bayWidth * 0.75, this.y0 - 7), str, "#0000ff");
//                text.draw(csanvasContext);
            }
            //画排号
            for (var i = 0; i < this.rowNum; i++) {
                var y="";
                y = this.y0 + (i + 1) * this.rowHeight;
//                if (this.rowSeq == '0') {//排序：0上->下，1下->上,
//                    y = this.y0 + (i + 1) * this.rowHeight;
//                }else{
//                    y = this.y0 + (this.rowNum - i) * this.rowHeight;
//                }
                var str = i + 1;
                /*if (str < 10) {
                    str = '0' + str;
                }*/
//                str=str_pad(str,rowNoWidth);
                str=str_pad(str,2);
                var text = new Text(new XY(this.x0 - 15, y - this.rowHeight * 0.5), str, "#0000ff");
//                text.draw(canvasContext);
            }
        } else {
            /*特殊场区*/
            var x1y1 = new XY(x0, y0);
            var x2y2 = new XY(x0 + this.bayNum * this.bayWidth, y0);
            var x3y3 = new XY(x0 + this.bayNum * this.bayWidth, y0 + this.rowNum * this.rowHeight);
            var x4y4 = new XY(x0, y0 + this.rowNum * this.rowHeight);
            var line1 = new Line(x1y1, x3y3, 1, "#000000");
            line1.draw(canvasContext);
            var line2 = new Line(x4y4, x2y2, 1, "#000000");
            line2.draw(canvasContext);
        }
    }
}
var bayWidth = 20;//贝的宽度
var rowHeight = 10;//排的高度
var columns = 2;//如果没有指定起点坐标，设置画布的列，然后计算起点坐标(暂时不用)
var margin = 50;//页面边距
var padding_x = parseInt(bayWidth * 2);//列与列的间距
var padding_y = parseInt(rowHeight * 2);//行间距

var started = false;//鼠标是否按下的标志，单纯按下左键
var isMove = false;//鼠标是否移动的标志
var ctrlDown = false;//ctrl键按下的标志
var shiftDown = false;//shift键按下的标志
var altDown = false;//shift键按下的标志
var startx0, starty0, startx1, starty1; //鼠标按下的起始坐标和抬起的结束坐标

/**
 * 图层
 * @param {type} id图层id
 * @param {type} z_index图层的z-index
 * @returns {CanvasLayer}
 */
function CanvasLayer(id, z_index) {
    this.id = id;//id图层id
    this.z_index = z_index;//图层的z-index
    this.width = 0;//宽度 （用画布的宽度）
    this.height = 0;//高度 （用画布的高度）
    this.canvasLayer = null;//document.getElementById("id")
    this.canvasLayerContext = null;//canvasLayer.getContext("2d")
    this.setId = function (id) {
        this.id = id
    }
    this.getId = function () {
        return this.getId()
    }
    this.setZ_index = function (z_index) {
        this.z_indexz_index;
    }
    this.getZ_index = function () {
        return this.z_index;
    }
    this.setWidth = function (width) {
        this.width = width;
    }
    this.getWidth = function () {
        return this.width;
    }
    this.setHeight = function (height) {
        this.height = height;
    }
    this.getHeight = function () {
        return this.height;
    }
    //根据canvas的id获取html element 
    this.getCanvasLayer = function () {
        if (this.canvasLayer == null) {
            this.canvasLayer = document.getElementById(this.id);
        }
        return this.canvasLayer;
    }
    /*获取context*/
    this.getCanvasLayerContext = function () {
        if (this.canvasLayerContext == null) {
            var canvasLayer = this.getCanvasLayer();
            if (canvasLayer != null) {
                this.canvasLayerContext = canvasLayer.getContext("2d");
            }
        }
        return this.canvasLayerContext;
    }
    //绘制
    this.draw = function (graphics) {
        if (this.getCanvasLayerContext() != null) {
            for (var i = 0; i < graphics.length; i++) {
                if (graphics[i] != null) {
                    graphics[i].draw(this.canvasLayerContext);
                }
            }
        }
    }
    //清空图层
    this.clearRect=function(startX, startY, rectwidth, rectheight){
      var startX= startX==null?0:startX;
      var startY= startY==null?0:startY;
      var rectwidth = rectwidth==null?this.width:rectwidth;
      var rectheight= rectheight==null?this.height:rectheight;
      this.getCanvasLayerContext().clearRect(startX, startY, rectwidth, rectheight);
    }
    //显示
    this.show = function () {
        $("#" + this.id).show();
    }
    //隐藏
    this.hide = function () {
        $("#" + this.id).hide();
    }
    //绑定事件
    this.bindEvent = function () {
        var thisCanvasLayer = this;
        //ctrl按键按下事件
        window.addEventListener('keydown', function (e) {
            var keyID = e.keyCode ? e.keyCode : e.which;
            if (keyID === 17) { // ctrl
                ctrlDown = true;
                e.preventDefault();
            }
            if (keyID === 16) { //Shift
            	shiftDown = true;
                e.preventDefault();
            }
            if (keyID === 18) { //Shift
            	altDown = true;
                e.preventDefault();
            }
        }, true);
         //ctrl按键抬起事件
        window.addEventListener('keyup', function (e) {
            var keyID = e.keyCode ? e.keyCode : e.which;
            if (keyID === 17) { // ctrl
                ctrlDown = false;
                e.preventDefault();
            }
            if (keyID === 16) { // ctrl
            	shiftDown = false;
                e.preventDefault();
            }
            if (keyID === 18) { // ctrl
            	altDown = false;
                e.preventDefault();
            }
        }, true);
        thisCanvasLayer.getCanvasLayer().addEventListener("mousedown", function (event) {
           // thisCanvasLayer.clearRect();
            var loc = new XY(event.offsetX, event.offsetY);
            startx0 = loc.getX();
            starty0 = loc.getY();
//            if ($.isFunction(callback)) {
//                callback(event, loc);
//            }
            if (ctrlDown) {//按住ctrl键点击左键 调用回调函数onCtrlClick，返回event和当前坐标
                if ($.isFunction(thisCanvasLayer.onCtrlClick)) {
                    thisCanvasLayer.onCtrlClick(event,loc);
                }
                ctrlDown = false;
            }else if (shiftDown) {//按住shift键点击左键 调用回调函数onShiftClick，返回event和当前坐标
                if ($.isFunction(thisCanvasLayer.onShiftClick)) {
                    thisCanvasLayer.onShiftClick(event,loc);
                }
                shiftDown = false;
            }else if (altDown) {//按住shift键点击左键 调用回调函数onShiftClick，返回event和当前坐标
                if ($.isFunction(thisCanvasLayer.onAltClick)) {
                    thisCanvasLayer.onAltClick(event,loc);
                }
                altDown = false;
            } else {
                if ($.isFunction(thisCanvasLayer.onClick)) {
                    thisCanvasLayer.onClick(event,loc);
                }
                started = true;
            }
        }, false);
        //鼠标拖拽 调用回调函数 onDrag 返回event和起始点坐标
        thisCanvasLayer.getCanvasLayer().addEventListener("mousemove", function (event) {
            var loc = new XY(event.offsetX, event.offsetY);
            startx1 = loc.getX();
            starty1 = loc.getY();
            if (started) {
                if (startx1 != startx0 && starty1 != starty0) {
                    isMove = true;
                    if ($.isFunction(thisCanvasLayer.onDrag)) {
                        thisCanvasLayer.onDrag(event,new XY(startx0,starty0),new XY(startx1,starty1));
                    }
                }
            }
        }, false);
        //拖拽结束 调用回调函数onDragEnd 返回event和起始点坐标
        thisCanvasLayer.getCanvasLayer().addEventListener("mouseup", function (event) {      
            var loc = new XY(event.offsetX, event.offsetY);
            if (started) {
                started = false;
                startx1 = loc.getX();
                starty1 = loc.getY();
                if (isMove) {
                    if ($.isFunction(thisCanvasLayer.onDragEnd)) {
                        thisCanvasLayer.onDragEnd(event, new XY(startx0,starty0), new XY(startx1,starty1));
                    }
                    isMove = false;
                }else{
                	if ($.isFunction(thisCanvasLayer.onMouseUp)) {
                		
                    thisCanvasLayer.onMouseUp(event, new XY(startx0,starty0));
                	}
                }
            }
        }, false);
    }
}
/**
 * 画布
 * @param {type} parentId 上一级div的id
 * @param {type} width 宽度
 * @param {type} height 高度
 * @returns {Canvas}
 */
function Canvas(parentId, width, height) {
    this.parentId = parentId;
    this.width = width;
    this.height = height;
    this.canvasLayers = [];
    this.setParentId = function (parentId) {
        this.parentId = parentId
    }
    this.getParentId = function () {
        return this.parentId;
    }
    this.setWidth = function (width) {
        this.width = width;
    }
    this.getWidth = function () {
        return this.width;
    }
    this.setHeight = function (height) {
        this.height = height;
    }
    this.getHeight = function () {
        return this.height;
    }
    this.setCanvasLayer = function (canvasLayers) {
        this.canvasLayers = canvasLayers
    }
    this.getCanvasLayers = function () {
        return this.canvasLayers;
    }
    //给画布添加图层，并指定图层宽度和高度为画布的宽高 
    this.push = function (canvasLayer, noborder) {
      var border = "border: 1px black solid;";
      if (noborder) {
        border = "";
      }
        if (this.canvasLayers == null || this.canvasLayers == undefined) {
            this.canvasLayers == []
        } else {
            this.canvasLayers.push(canvasLayer);
            canvasLayer.setWidth(this.width);
            canvasLayer.setHeight(this.height);
            var html = "<Canvas id='" + canvasLayer.id + "'  width='" + this.width + "' height='" + this.height + "' style='position: absolute;"+border+"z-index:" + canvasLayer.z_index + ";'></canvas>";
            $("#" + this.parentId).append(html);
        }
    }
//    this.show = function (index) {
//        if (this.canvasLayers.length >= index) {
//            this.canvasLayers[index].show();
//        }
//    }
//    this.hide = function (index) {
//        if (this.canvasLayers.length >= index) {
//            this.canvasLayers[index].hide();
//        }
//    }
}
/*
 * 堆场布局图  
 * 整理查询出来的堆场数据，组成成需要的数据结构，供画图用
 * data：堆场数据
 * */
function CanvasBuilder(data) {
    this.data = data;
    this.width = null;
    this.height = null;
    this.layerData1 = null;//处理后的堆场边框、贝号和排号数据
    this.layerData2 = null;//处理后的堆场网格数据
    this.layerData3 = null;//处理后的在场箱数据
    this.layerData4 = null;//处理后的堆场计划数据
    this.layerData5 = null;//事件层，绑定事件
    this.setData = function (data) {
        this.data = data;
    }
    this.setWidth = function (width) {
        this.width = width;
    }
    this.getWidth = function () {
        return this.width;
    }
    this.setHeight = function (height) {
        this.height = height;
    }
    this.getHeight = function () {
        return this.height;
    }
    this.setLayerData1 = function (layerData1) {
        this.layerData1 = layerData1
    }
    this.getLayerData1 = function () {
        return this.layerData1;
    }
    this.setLayerData2 = function (layerData2) {
        this.layerData2 = layerData2
    }
    this.getLayerData2 = function () {
        return this.layerData2;
    }
    /**
     * 处理在场箱数据，整理成fillRec
     * @param {type} portCntrLs在场箱
     */
    this.setLayerData3 = function (portCntrLs) {
        var layerData3 = [];
        for (var i = 0; i < portCntrLs.length; i++) {
            var portCntr = portCntrLs[i];
            var fillRec = this.getFillRecByPortCntr(portCntr);
            layerData3.push(fillRec);
        }
        this.layerData3 = layerData3;
    }
    this.getLayerData3 = function () {
        return this.layerData3;
    }
    this.setLayerData4 = function (layerData4) {
        this.layerData4 = layerData4;
    }
    this.getLayerData4 = function () {
        return this.layerData4;
    }
    /**
     * 
     * @param {type} type onDrag/onDragEnd
     * @param {type} startXY 起点坐标
     * @param {type} endXY终点坐标
     * @returns {undefined}
     */
    this.setLayerData5 = function (type,startXY,endXY) {
        var layerData5 = [];
        //拖拽的区域
        var fillRec=new FillRec(startXY, endXY.x-startXY.x, endXY.y-startXY.y, "rgb(255,255,0)",0.6);
        layerData5.push(fillRec);
        //如果type==onDragEnd 画坐标
        if(type=="onDragEnd"){
            //左右拖动标志
            var flag=1;
            
            if(startXY.x<endXY.x){//向右拖
                flag=1;
            }else{//向左拖
                flag=-1;
            }
            //上下拖动标志
            var flag2=1;
            if(startXY.y<endXY.y){//向下拖
                flag2=1;
            }else{
                flag2=-1;//向上拖
            }
            //纵轴
            var line1=new Line(new XY(startXY.x+flag*4,startXY.y+flag2*4), new XY(startXY.x+flag*4, endXY.y-flag2*4), 1, "#000000");
            layerData5.push(line1);
            //横轴
            var line2=new Line(new XY(startXY.x+flag*4, endXY.y-flag2*4), new XY(endXY.x-flag*4,endXY.y-flag2*4), 1, "#000000");
            layerData5.push(line2);
            //箭头
            var line3=new Line(new XY(endXY.x-flag*8,endXY.y-flag2*8), new XY(endXY.x-flag*4,endXY.y-flag2*4), 1, "#000000");
            layerData5.push(line3);
            var line4=new Line(new XY(endXY.x-flag*8, endXY.y), new XY(endXY.x-flag*4,endXY.y-flag2*4), 1, "#000000");
            layerData5.push(line4); 
        }
        this.layerData5 = layerData5;
    }
    this.getLayerData5 = function () {
        return this.layerData5;
    }
    /**
     * 初始化，处理堆场数据，整理出堆场和网格数据,即layerData1和layerData2
     */
    this.init = function () {
        //堆场数据
        var areaArr = [];
        //堆场网格
        var grids = [];
        //堆场最大横坐标
        var maxX = 0;
       //堆场最大纵坐标
        var maxY = 0;
        for (var i = 0; i < this.data.length; i++) {
            var cyArea = this.data[i];
            if (cyArea != null) {
                //当前场最大横坐标
                var x = cyArea.x0 + bayWidth * cyArea.bayNum;
                //当前场最大纵坐标
                var y = cyArea.y0 + rowHeight * cyArea.maxRowNum;
                /*把最大值给maxX和maxY*/
                if (x > maxX) {
                    maxX = x;
                }
                if (y > maxY) {
                    maxY = y;
                }
                //new一个堆场 并push到areaArr
                var area = new Area(cyArea.cyAreaId, cyArea.cyAreaNo, cyArea.x0, cyArea.y0, cyArea.bayNum, cyArea.maxRowNum, cyArea.maxTierNum, cyArea.schemeId, cyArea.cyBaySeq, cyArea.cyRowSeq);
                areaArr.push(area);
                //每个场的线 
                var lines = [];
                //每个场的贝
                var bays=[];
                //普通场
                if (cyArea.schemeId != "-1") {
                    //网格横线
                    for (var j = 0; j <=cyArea.bayNum - 1; j++) {
                        var x1 = cyArea.x0 + (j + 1) * bayWidth;
                        var y1 = cyArea.y0;
                        var x2 = x1;
                        var y2 = y1 + rowHeight * cyArea.maxRowNum;
                        var line = new Line(new XY(x1-0.5, y1), new XY(x2-0.5, y2), 1, "#000000");
                        lines.push(line);
                        //初始化当前场贝
                        if(cyArea.cyBaySeq=='0'){//正序
                            bays[j]=new Bay(2*j+1,cyArea.x0 + j * bayWidth,y1,cyArea.maxRowNum,cyArea.cyRowSeq);
                        }else{//倒序
                            bays[cyArea.bayNum-1-j]=new Bay(2*(cyArea.bayNum-1-j)+1,cyArea.x0 + j * bayWidth ,y1,cyArea.maxRowNum,cyArea.cyRowSeq);
                        }
                        //给当前场setbays
                        area.setBays(bays);
                    }
                    //网格竖线
                    for (var k = 0; k < cyArea.maxRowNum - 1; k++) {
                        var x1 = cyArea.x0;
                        var y1 = cyArea.y0 + (k + 1) * rowHeight;
                        var x2 = x1 + bayWidth * cyArea.bayNum;
                        var y2 = y1;
                        var line = new Line(new XY(x1, y1 - 0.5), new XY(x2, y2 - 0.5), 1, "#000000");
                        lines.push(line);
                    }
                }
            }
            //一个场的网格
            var grid = new Grid(lines);
            grids.push(grid);
        }
        // 画布宽度
        this.setWidth(maxX + margin);
        //画布高度
        this.setHeight(maxY + margin);
        //把堆场数据areaArr 赋值给layerData1
        this.setLayerData1(areaArr);
        //把网格数据赋值给layerData2
        this.setLayerData2(grids);
    }
    //初始化，new的时候 就自动调用
    //this.init();
    /*
     * 根据在场箱信息 返回矩形块
     * @param {type} portCntr 在场箱信息
     * @returns {FillRec}
     */
    this.getFillRecByPortCntr = function (portCntr) {
        var fillRec = null;
        //矩形块（即一个在场箱）x1起点横坐标y1起点纵坐标 width宽度 height高度
        var x1, y1, width, height = rowHeight, color;
        //this.data(堆场信息)根据箱子场贝排计算起点坐标
        for (var i = 0; i < this.data.length; i++) {
            var cyArea = this.data[i];
            //所在场
            if (portCntr.cyAreaNo == cyArea.cyAreaNo) {
                //所在场的起始坐标
                var x0 = cyArea.x0;
                var y0 = cyArea.y0;
                //根据贝序和贝号 计算所在贝的起点横坐标
                if (cyArea.cyBaySeq == '0') {//贝序：0左->右，1右->左
                    x1 = x0 + Math.floor((Number(portCntr.cyBayNo) - 1) / 2) * bayWidth;
                } else {
                    x1 = (x0 + cyArea.bayNum * bayWidth) - (Math.floor(Number(portCntr.cyBayNo) / 2) + 1) * bayWidth;
                }
                //根据排号和排序，计算所在贝的纵坐标信息
                if (cyArea.cyRowSeq == '0') {//排序：0上->下，1下->上
                    y1 = y0 + (Number(portCntr.cyRowNo) - 1) * rowHeight;
                } else {
                    y1 = (y0 + rowHeight * cyArea.maxRowNum) - Number(portCntr.cyRowNo) * rowHeight;
                }
                //根据箱尺寸，确定宽度
                if (Number(portCntr.cntrSizCod) <= 20) {
                    width = bayWidth;
                } else {
                    width = 2 * bayWidth;
                }
                //根据空重，确定颜色
                if (portCntr.efId == 'E') {
                    color = "rgb(0,255,64)";//空箱绿色
                } else {
                    color = "rgb(255,0,0)"//重箱红色
                }
                fillRec = new FillRec(new XY(x1, y1), width-1, height-1, color);
                break;
            }

        }
        return fillRec;
    }
    /**
     * 根据坐标 获取场贝(排 还未实现)
     * @param {XY} xy
     * @returns {CanvasBuilder.getAreaBayByXY.area|Area}
     */
    this.getAreaBayByXY=function(xy){
        var areas=this.layerData1;
        if(areas!=null){
            var x0,y0,x1,y1;
            for(var i=0;i<areas.length;i++){
                var cyArea=areas[i];
                var area=new Area(cyArea.areaId, cyArea.areaNo, cyArea.x0, cyArea.y0, cyArea.bayNum, cyArea.rowNum, cyArea.tierNum, cyArea.schemeId, cyArea.baySeq, cyArea.rowSeq);
                 x0=area.x0;
                 y0=area.y0;
                 x1=x0+area.bayNum*bayWidth;
                 y1=y0+area.rowNum*rowHeight;
                if(xy.x>=x0&&xy.y>=y0&&xy.x<=x1&&xy.y<=y1){
                    if(cyArea.bays!=null){
                        for(var j=0;j<cyArea.bays.length;j++){
                            var bay=cyArea.bays[j];
                            x0=bay.x0;
                            y0=bay.y0;
                            x1=x0+bayWidth;
                            y1=y0+cyArea.rowNum*rowHeight;
                            if(xy.x>=x0&&xy.y>=y0&&xy.x<=x1&&xy.y<=y1){
                                area.setBays([bay]);
                                return area;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return null;
    }
    /**
     * 根据起始点坐标，得到选中区域的场贝的起始点坐标
     * @param {XY} x1y1
     * @param {XY} x2y2
     * @returns {CanvasBuilder.getResetXY.startend} 返回值起始点坐标
     */
    this.getResetXY=function(x1y1,x2y2){
        //返回值起始点坐标
        var startend=null;
        var areas=this.layerData1;
        if(areas!=null){
            var x0,y0,x1,y1;
            for(var i=0;i<areas.length;i++){
                var cyArea=areas[i];
                 x0=cyArea.x0;
                 y0=cyArea.y0;
                 x1=x0+cyArea.bayNum*bayWidth;
                 y1=y0+cyArea.rowNum*rowHeight;
                 //x1y1和x2y2在同一个对场内
                if(x1y1.x>=x0&&x1y1.y>=y0&&x1y1.x<=x1&&x1y1.y<=y1){//x1y1在对场内
                    if(x2y2.x>=x0&&x2y2.y>=y0&&x2y2.x<=x1&&x2y2.y<=y1){//x2y2在堆场内
                       var area=new Area(cyArea.areaId, cyArea.areaNo, cyArea.x0, cyArea.y0, cyArea.bayNum, cyArea.rowNum, cyArea.tierNum, cyArea.schemeId, cyArea.baySeq, cyArea.rowSeq);
                       startend={};
                       if(cyArea.bays!=null){
                            for(var j=0;j<cyArea.bays.length;j++){
                                var bay=cyArea.bays[j];
                                for(var k=0;k<bay.places.length;k++){
                                    var place=bay.places[k];
                                    //确定起点的场位
                                    if(x1y1.x>=place.x0&&x1y1.x<=place.x0+bayWidth&&x1y1.y>=place.y0&&x1y1.y<=place.y0+rowHeight){
                                        //向右拖拽
                                        if(x1y1.x<=x2y2.x){
                                            //拖拽方向：右下
                                            if(x1y1.y<=x2y2.y){
                                                //起点坐标:所在场位的起点坐标
                                                startend.start=new XY(place.x0,place.y0);
                                            }else{
                                                //拖拽方向：右上
                                                //起点坐标:所在场位的左下角坐标
                                                startend.start=new XY(place.x0,place.y0+rowHeight);
                                            }
                                        }else{//向左拖拽 
                                            if(x1y1.y<=x2y2.y){
                                                //拖拽方向：左下
                                                //起点坐标：所在场位的右上角坐标
                                                startend.start=new XY(place.x0+bayWidth,place.y0);
                                            }else{
                                                //拖拽方向：左上
                                                //起点坐标：所在场位的右下角坐标
                                                startend.start=new XY(place.x0+bayWidth,place.y0+rowHeight);
                                            }
                                        }
                                    }
                                    //确定终点坐标的场位
                                    if(x2y2.x>=place.x0&&x2y2.x<=place.x0+bayWidth&&x2y2.y>=place.y0&&x2y2.y<=place.y0+rowHeight){
                                        //向右拖拽
                                        if(x1y1.x<=x2y2.x){
                                            //拖拽方向：右下
                                            if(x1y1.y<=x2y2.y){
                                                //终点坐标:所在场位的右下角坐标
                                                startend.end=new XY(place.x0+bayWidth,place.y0+rowHeight);
                                            }else{ 
                                                 //拖拽方向：右上
                                                //终点坐标：所在场位的右上角坐标
                                                startend.end=new XY(place.x0+bayWidth,place.y0);
                                            }
                                        }else{
                                            //拖拽方向:左下
                                            if(x1y1.y<=x2y2.y){
                                                //终点坐标：所在场位的左下角坐标
                                                startend.end=new XY(place.x0,place.y0+rowHeight);
                                            }else{
                                                 //拖拽方向:左上
                                                //终点坐标：所在场位的起点坐标
                                                startend.end=new XY(place.x0,place.y0);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return startend;
                    }else{
                        return null;;
                    }
                }
            }
        }
        return null;
    }
    this.build = function (type) {
        if (this.data != null) {
            switch (type) {
                //第一层画布（z_index=1）,返回Area[];
                case 1:
                    return this.getLayerData1();
//                    var areaArr=[];
//                    var grids=[];
//                    var maxX=0;
//                    var maxY=0;
//                    for(var i=0;i<this.data.length;i++){
//                        var cyArea=this.data[i];
//                        if(cyArea!=null){
//                            var x=cyArea.x0+bayWidth*cyArea.bayNum;
//                            var y=cyArea.y0+rowHeight*cyArea.maxRowNum;
//                            if(x>maxX){
//                                maxX=x;
//                            }
//                            if(y>maxY){
//                                maxY=y;
//                            }
//                           var area=new Area(cyArea.cyAreaId,cyArea.cyAreaNo,cyArea.x0,cyArea.y0,cyArea.bayNum,cyArea.maxRowNum,cyArea.maxTierNum,cyArea.schemeId,cyArea.cyBaySeq,cyArea.cyRowSeq); 
//                           areaArr.push(area);
//                        }
//                    }
//                    this.setWidth(maxX+margin);
//                    this.setHeight(maxY+margin);
//                    return areaArr;
                    break;
                case 2:
                    ;
                    return this.getLayerData2();
//                    var grids=[];
//                    for(var i=0;i<this.data.length;i++){
//                        var lines=[];
//                        var cyArea=this.data[i];
//                        if(cyArea!=null&&cyArea.schemeId!="-1"){
//                            for(var j=0;j<cyArea.bayNum-1;j++){
//                                var x1=cyArea.x0+(j+1)*bayWidth;
//                                var y1=cyArea.y0;
//                                var x2=x1;
//                                var y2=y1+rowHeight*cyArea.maxRowNum;
//                                var line=new Line(new XY(x1,y1),new XY(x2,y2),1,"#000000");
//                                lines.push(line);
//                            }
//                            for(var k=0;k<cyArea.maxRowNum-1;k++){
//                                var x1=cyArea.x0;
//                                var y1=cyArea.y0+(k+1)*rowHeight;
//                                var x2=x1+bayWidth*cyArea.bayNum;
//                                var y2=y1;
//                                var line=new Line(new XY(x1,y1),new XY(x2,y2),1,"#000000");
//                                lines.push(line);
//                            }
//                        }
//                        
//                        var grid=new Grid(lines);
//                        grids.push(grid);
//                    }
//                    return grids;
                    break
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    break;
            }
        }
    }
}

/*堆场定义不需要定义起始坐标，自动画图*/
function draw(cyAreaLs) {
    if (cyAreaLs != null) {
        var rows = Math.ceil(cyAreaLs.length / columns);
        var maxRowsByRow = new Array(rows);
        var maxBaysByColumn = new Array(columns);
        for (var i = 0; i < cyAreaLs.length; i++) {
            var area = cyAreaLs[i];
            var index_x = i % columns;//第index_x列
            var index_y = parseInt(i / columns);//第index_y行
            for (var j = 0; j < columns; j++) {
                if (index_x == j) {
                    if (maxBaysByColumn[j]) {
                        if (maxBaysByColumn[j] < area.bayNum) {
                            maxBaysByColumn[j] = area.bayNum;
                        }
                    } else {
                        maxBaysByColumn[j] = area.bayNum;
                    }
                }
            }
            for (var k = 0; k < rows; k++) {
                if (index_y == k) {
                    if (maxRowsByRow[k]) {
                        if (maxRowsByRow[k] < area.maxRowNum) {
                            maxRowsByRow[k] = area.maxRowNum;
                        }
                    } else {
                        maxRowsByRow[k] = area.maxRowNum;
                    }
                }
            }
        }
        var areas = [];
        for (var i = 0; i < cyAreaLs.length; i++) {
            var cyArea = cyAreaLs[i];
            if (cyArea) {
                var index_x = i % columns;//第index_x列
                var index_y = parseInt(i / columns);//第index_y行
                var x0 = margin + index_x * (maxBaysByColumn[index_x] * bayWidth + padding_x);
                var y0 = margin;
                for (var j = 0; j < index_y; j++) {
                    y0 += maxRowsByRow[j] * rowHeight + padding_y;
                }
                // var xy=new  XY(x0,y0);
                // console.log(cyArea.cyAreaNo);
                // console.log(xy)
                var area = new Area(cyArea.cyAreaId, cyArea.cyAreaNo, x0, y0, cyArea.bayNum, cyArea.maxRowNum, cyArea.maxTierNum, cyArea.schemeId, cyArea.cyBaySeq, cyArea.cyRowSeq);
                areas.push(area);
            }
        }
        var canvas1 = new CanvasLayer("CanvasDiv1", "CanvasDiv", 3000, 1500, 1, areas);
        canvas1.draw();
    }
}

function SpecAreaAndCntr(areaNo,x0,y0,width,height,cntrs){
  this.areaNo=areaNo;
  this.x0=x0;
  this.y0=y0;
  this.width=width;
  this.height=height;
  this.cntrs=cntrs;
  this.draw=function(canvasContext){
    var _this=this;
    canvasContext.fillStyle = "#000000";
    canvasContext.fillText(_this.areaNo, _this.x0, _this.y0 - 4);
    canvasContext.strokeStyle = "#000000";
    canvasContext.lineWidth = 1;
    var strokeRec = new StrokeRec(new XY(_this.x0, _this.y0), _this.width, _this.height, "#000000");
    strokeRec.draw(canvasContext);
//    var textHeadStr=" 箱型    尺寸    空重    数量 ";
//    var textHeadStr=" "+$.messager.defaults.cntrSpecSum['箱型']+
//    "    "+$.messager.defaults.cntrSpecSum['尺寸']+
//    "    "+$.messager.defaults.cntrSpecSum['空重']+
//    "    "+$.messager.defaults.cntrSpecSum['数量'];
//   var text0=new Text(new XY(_this.x0+5,_this.y0+15),textHeadStr,"#0000ff");
//    text0.draw(canvasContext);
//    if(_this.cntrs&&_this.cntrs.length>0){
//      for(var i=0;i<_this.cntrs.length;i++){
//        var  cntr=_this.cntrs[i];
//        var textStr="  "+cntr.cntrType+"      "+cntr.cntrSize+"      "+ cntr.efFlag+"       "+cntr.cntrNum+"   ";
//        var text=new Text(new XY(_this.x0+5,_this.y0+(i+2)*15),textStr,"#0000ff");
//        text.draw(canvasContext);
//      }
//    }
    if(_this.cntrs&&_this.cntrs.length>0){
      var textStr="";
      var j=0;
      _this.x0+=5;
      //_this.y0+=10;
      var sum=0;
      var s=parseInt(_this.width/70);
    //  _this.x0+=(_this.width-s*70)/2;
      if(900*(_this.cntrs.length+1)>_this.width*_this.height){
        for(var i=0;i<_this.cntrs.length;i++){
          var  cntr=_this.cntrs[i];
          sum+=cntr.cntrNum;
        }
        var tot="TOTAL:"+sum;
        text=new Text(new XY(_this.x0,_this.y0+15),tot,"#0000ff");
        text.draw(canvasContext);
      }else{
        for(var i=0;i<_this.cntrs.length;i++){
          var  cntr=_this.cntrs[i];
          sum+=cntr.cntrNum;
          textStr+=cntr.cntrSize+"/"+cntr.cntrType+"/"+ cntr.efFlag+":"+cntr.cntrNum+", ";
            if((i+1)%s===0){
              _this.y0+=15;
              var text=new Text(new XY(_this.x0,_this.y0),textStr.substring(0,textStr.length-2),"#0000ff");
              text.draw(canvasContext);
              textStr="";
              if(i+1===_this.cntrs.length){
                var tot="TOTAL:"+sum;
                text=new Text(new XY(_this.x0,_this.y0+15),tot,"#0000ff");
                text.draw(canvasContext);
              }
            }else{
              if(i+1===_this.cntrs.length){
                _this.y0+=15;
                var text=new Text(new XY(_this.x0,_this.y0),textStr.substring(0,textStr.length-2),"#0000ff");
                text.draw(canvasContext);
                var tot="TOTAL:"+sum;
                text=new Text(new XY(_this.x0,_this.y0+15),tot,"#0000ff");
                text.draw(canvasContext);
              }
            }
        }
      }
   
    }
  }
}
function SpecArea(areaNo,x0,y0,width,height,cntrs){
  this.areaNo=areaNo;
  this.x0=x0;
  this.y0=y0;
  this.width=width;
  this.height=height;
  this.draw=function(canvasContext){
    var _this=this;
    canvasContext.fillStyle = "#000000";
    canvasContext.fillText(_this.areaNo, _this.x0, _this.y0 - 4);
    canvasContext.strokeStyle = "#000000";
    canvasContext.lineWidth = 1;
    var strokeRec = new StrokeRec(new XY(_this.x0, _this.y0), _this.width, _this.height, "#000000");
    strokeRec.draw(canvasContext);
   //创建新的图片对象
    var img = new Image();
    //指定图片的URL
    img.src = webRoot + "/static/image/spec_area_background.png?t="+(new Date().getTime());
  //浏览器加载图片完毕后再绘制图片
    img.onload = function(){
      //以Canvas画布上的坐标(x0,y0)为起始点，绘制图像
      canvasContext.drawImage(img, 0,0,_this.width-0.5,_this.height-0.5,_this.x0,_this.y0,_this.width-0.5, _this.height-0.5);
      canvasContext.fillStyle = "#000000";
      canvasContext.fillText(_this.areaNo, _this.x0, _this.y0 - 4);
      canvasContext.strokeStyle = "#000000";
      canvasContext.lineWidth = 1;
      var strokeRec = new StrokeRec(new XY(_this.x0, _this.y0), _this.width, _this.height, "#000000");
      strokeRec.draw(canvasContext);
    }
  }
}

function SpecAreas(specAreas){
  this.specAreas=specAreas;
  this.draw=function(canvasContext){
    var _this=this;
    //创建新的图片对象
    var img = new Image();
    //指定图片的URL
    img.src = webRoot + "/static/image/spec_area_background.png?t="+(new Date().getTime());
  //浏览器加载图片完毕后再绘制图片
    img.onload = function(){
      for(var i=0;i<_this.specAreas.length;i++){
        var this_specArea=_this.specAreas[i];
      //以Canvas画布上的坐标(x0,y0)为起始点，绘制图像
        canvasContext.drawImage(img, 0,0,this_specArea.width-0.5,this_specArea.height-0.5,this_specArea.x0,this_specArea.y0,this_specArea.width-0.5, this_specArea.height-0.5);
        canvasContext.fillStyle = "#000000";
        canvasContext.fillText(this_specArea.areaNo, this_specArea.x0, this_specArea.y0 - 4);
        canvasContext.strokeStyle = "#000000";
        canvasContext.lineWidth = 1;
        var strokeRec = new StrokeRec(new XY(this_specArea.x0, this_specArea.y0), this_specArea.width, this_specArea.height, "#000000");
        strokeRec.draw(canvasContext);
      }
    };
  }
}

function Cntr3D(xy,cntrSize,efFlag,color){
  this.xy=xy;
  this.cntrSize=cntrSize;
  this.efFlag=efFlag;
  this.color=color;
  this.draw = function (canvasContext) {
    var _this=this;
    if(efFlag === "E"){
      _this.color =_this.color?_this.color: "#1AE61A";//空箱显示绿色
    }else{
      _this.color = _this.color?_this.color: "#EE113D";  //重箱显示红色
    }
    var cntrsizeNum = Number(cntrSize) / 20;
    //var cntrLoc=_this.xy;
    var cntrx0=_this.xy.x;
    var cntry0=_this.xy.y;
    var width,height;
    if(cntrsizeNum<=1){//判断40/20尺
        width=baywidth-1-3;
        height=rowheight-1-3;
    }else{
        width=baywidth*2-1-3;
        height=rowheight-1-3;
    }
    var fillrect = new FillRec(new XY(cntrx0-0.5, cntry0-0.5), width, height, _this.color,"1");
    var strokerect = new StrokeRec(new XY(cntrx0-0.5, cntry0-0.5), width, height, "#000000");//矩形
    fillrect.draw(canvasContext);
    strokerect.draw(canvasContext);
  
    var xyArr1=[
             new XY(cntrx0-0.5+width,cntry0-0.5), 
             new XY(cntrx0-0.5+width+3,cntry0-0.5+3), 
             new XY(cntrx0-0.5+width+3,cntry0-0.5+height+3), 
             new XY(cntrx0-0.5+width,cntry0-0.5+height)
            ];
    var strokePolygon1 = new StrokePolygon(xyArr1, "#000000");//多边形
    strokePolygon1.draw(canvasContext);
    var fillPolygon1 = new FillPolygon(xyArr1, _this.color, 1);
    fillPolygon1.draw(canvasContext);
    var xyArr2=[
               new XY(cntrx0-0.5,cntry0-0.5+height), 
               new XY(cntrx0-0.5+width,cntry0-0.5+height), 
               new XY(cntrx0-0.5+width+3,cntry0-0.5+height+3), 
               new XY(cntrx0-0.5+3,cntry0-0.5+height+3)
              ];
      var strokePolygon2 = new StrokePolygon(xyArr2, "#000000");//多边形边框
      strokePolygon2.draw(canvasContext);
      var fillPolygon2 = new FillPolygon(xyArr2, _this.color, 1);
      fillPolygon2.draw(canvasContext);
  }
}
