/* 
 * ezui form鎵╁睍
 */
$.extend($.fn.form.defaults, {
	originalData : {}
});

/*
 * ezui form method鎵╁睍
 */
$
		.extend(
				$.fn.form.methods,
				{
					/**
					 * 杩斿洖form灞炴�у璞°��
					 * 
					 * @function
					 * @name form#options
					 */
					options : function(jq) {
						return $.data(jq[0], 'form').options;
					},
					/**
					 * 鍔犺浇鏁版嵁锛堜笉鐩存帴浣跨敤 form('load')鏂规硶,鍥犱负鏃ユ湡銆乨isabled琛ㄥ崟绛夋暟鎹渶瑕佸鐞嗭級銆�
					 * 
					 * @function
					 * @name form#loadData
					 */
					loadData : function(jq, data) {
						return jq
								.each(function() {
									// 0 CHECK
									if (!$.isPlainObject(data)) {
										data = $.evalJSON(data); // 纭繚鏄璞�
									}
									if (!$.isPlainObject(data)) {
										alert("鍙戠幇缂栫▼閿欒锛氳皟鐢‵ormOperation.loadData缁欏畾鍙傛暟涓嶆槸瀵硅薄涔熶笉鏄疛SON鏍煎紡");
										return false; // 涓嶆槸瀵硅薄锛屼篃涓嶆槸瀵硅薄JSON鏃犳硶澶勭悊浜�
									}
									// 1 CLEAR
									var form = $(this);
									form.form('clear'); // 闃叉load鐨勬暟鎹煇浜涘瓧娈典负绌�,鏃犳硶瑕嗙洊鍘熸湁鐨勮〃鍗曞��

									// 2 淇濆瓨鍘熷鏁版嵁
									$.fn.form.defaults.originalData = $.extend(
											{}, data, true);
									form.data("oldValue", form.serialize()); // 鐢ㄤ簬鍒ゆ柇琛ㄥ崟鏁版嵁鏄惁淇敼

									if (form.data('isFirst') == undefined) {
										form.data('isFirst', 1);
									} else {
										form.data('isFirst', 0);
									}
									// 3 FORM LOAD
									$.hd.form.load($(this), data);
									return true;
								});
					},
					/**
					 * add by huxj 2016-10-14
					 * dialog鐗规畩澶勭悊锛屾瘮濡傛墦寮�dialog涓嶇珛鍗宠皟鐢╨oadData鏂规硶锛岃�屾槸鍦ㄨ姹傜浉鍏虫暟鎹悗鍦ㄧ偣鐢╨oadData锛岄渶瑕佸厛鎵ц璇ユ柟娉�
					 * 渚嬪锛�$("#formID").form('setIsFirst', 0);
					 * 
					 * @function
					 * @name form#setIsFirst
					 */
					setIsFirst : function(jq, value) {
						return jq.each(function() {
							var form = $(this);
							form.data('isFirst', value);
						});
					},
					/**
					 * 鑾峰緱FORM鏁版嵁锛屼互JSON涓茶繑鍥炪��
					 * 
					 * @function
					 * @name form#getData
					 */
					getData : function(jq) {
						var ret;
						jq.each(function(){
							ret = $.hd.form.getDataAsObject($(this));
						});
						return ret;
					},
					/**
					 * 娓呯┖FORM鏁版嵁锛屽苟淇濆瓨old鍊笺��
					 * 
					 * @function
					 * @name form#clearData
					 */
					clearData : function(jq) {
						jq.each(function(){
							$(this).form('clear');
							$(this).data("oldValue", $(this).serialize()); // 杩欓噷涓嶈兘璧嬩簣NULL锛屽惁鍒欐棤浠庡垽瀹�
							// 鏈�鏂扮殑serialize()鏄笉鏄敼杩囩殑
							$.fn.form.defaults.originalData = {};
						});
					},
					/**
					 * 鏍囪涓烘柊FORM銆�
					 * 
					 * @function
					 * @name form#markAsNew
					 */
					markAsNew : function() {
						$.fn.form.defaults.originalData = {};
						$(this).data("oldValue", $(this).serialize()); // 鐢ㄦ埛鐩存帴clear鑰屼笉loadData锛屽皢瀵艰嚧oldValue涓簄ull鏈�缁堝垽瀹氫负鏈敼鍙�
					},
					/**
					 * 鏍囪FORM鏈敼鍙樸��
					 * 
					 * @function
					 * @name form#markAsUnmodified
					 */
					markAsUnmodified : function() {
						$(this).data("oldValue", $(this).serialize());
						$.fn.form.defaults.originalData = $.hd.form
								.getDataAsObject($(this));
					},
					/**
					 * 杩樺師FORM涓轰箣鍓嶇殑鍊笺��
					 * 
					 * @function
					 * @name form#undo
					 */
					undo : function() {
						$(this).form('reset');
						$(this).loadData($.fn.form.defaults.originalData);
						$(this).data("oldValue", $(this).serialize());
					},
					/**
					 * 妫�鏌ORM鏄惁鏇存敼銆�
					 * 
					 * @function
					 * @name form#isChanged
					 */
					isChanged : function() {
						var oldValue = $(this).data("oldValue");
						var newValue = $(this).serialize();

						if (oldValue != null && oldValue != newValue
								|| oldValue == null && newValue != null) {
							return true;
						} else {
							return false;
						}
					},
					/**
					 * 鑾峰彇FORM鏇存敼鍊笺��
					 * 
					 * @function
					 * @param {string
					 *            or array} pkField銆�
					 * @name form#getChangedData
					 */
					/*getChangedData : function(jq, pkField) {
						var changedData = {};
						//var newData = $(this).getData();
						var newData = $.hd.form.getData(jq);
						$
								.each(
										newData,
										function(name, value) {
											if (value != $.fn.form.defaults.originalData[name]) {
												changedData[name] = value;
											}
										});
						if (!pkField) {
							return changedData;
						}
						if ($.type(pkField) === 'string') {
							changedData[pkField] = newData[pkField];
						} else if ($.isArray(pkField)) {
							$.each(pkField, function(i, value) {
								changedData[value] = newData[value];
							});
						}
						return changedData;
					},*/
					getChangedData: function (jq,oldData) {
				        var changedData = {};
				        var oldValue;
				        if(oldData!=null&&oldData!={}) {
				        	oldValue = oldData;
				        } else {
				        	oldValue = $.fn.form.defaults.originalData;
				        }
				    	var newData = JSON.parse($.hd.form.getData(jq));
				    	// datebox鍊艰浆鎴恉atetimebox鍊�
				        var temp2 = jq.find('.easyui-datebox');
				        $.each(temp2, function () {
				        	var timeName = this['attributes'][4]['value'];
				        	if (typeof (oldValue[timeName]) == "number") {
				        	    oldValue[timeName] = new Date(parseInt(oldValue[timeName])).format("yyyy-MM-dd");
				        	}
				        });
				         // datetimebox 杞崲鎴愭爣鍑嗘牸寮忋��
				        var temp4 = jq.find('.easyui-datetimebox');
				        $.each(temp4, function () {
				        	var timeName = this['attributes'][4]['value'];
				        	if (typeof (oldValue[timeName]) == "number") {
				        	    oldValue[timeName] = new Date(parseInt(oldValue[timeName])).format("yyyy-MM-dd hh:mm:ss");
				        	}
				        });
				        $.each(newData, function (name,value) {
				        	if (value != oldValue[name]) {
				            	if(value==""&&oldValue[name]==null) {
				            	} else {
				            		changedData[name] = value;
				            	}
				            }
				        });
				        for(var i in changedData){    
				        	if(changedData.hasOwnProperty(i)) {      
				        		return changedData;    
				    		}   
				    	}   
				        return false; 
				    },
					/**
					 * 淇濆瓨鏂规硶銆�
					 * 
					 * @function
					 * @param {object}
					 *            params {url:xxx,callback:function(){}}銆�
					 * @name form#submit
					 */
					submit : function(jq, params) {
						if (!$(this).form('validate')) {
							$.messager.alert(Resources.WB_INFO,
									Resources.INFO_INVALID, "info");
							return;
						}
						$.ajax({
							url : params.url,
							data : $.toJSON($(this).getData()),
							dataType : "json",
							type : 'POST',
							success : function(data) {
								HdUtils.messager.show(data, function() {
									if ($.isFunction(params.callback)) {
										params.callback();
									}
								});
							},
							error : function(data) {
								HdUtils.messager.show(data);
							}
						});
					},
					deleteData : function(jq, params) {
						if (!params.url || "" == params.url) {
							return;
						}
						if (!params.data || !$.isPlainObject(params.data)
								|| {} == params.data) {
							params.data = $(this).getData();
						}
						$.messager
								.confirm(
										Resources.WB_CONFIRM,
										Resources.WB_REMOVE_CONFIRM,
										function(r) {
											if (r) {
												$
														.ajax({
															url : params.url,
															type : 'POST',
															data : $
																	.toJSON(params.data),
															success : function(
																	data) {
																HdUtils.messager
																		.show(
																				data,
																				function() {
																					if (jQuery
																							.isFunction(params.callback)) {
																						params
																								.callback();
																					}
																				});
															},
															error : function(
																	data) {
																HdUtils.messager
																		.show(data);
															}
														});
											}
										});
					}
				});

$.hd = {};

$.extend($.hd, {
	form : {
		load : function(form, data) {
				$.each(form.find('.easyui-datetimebox'), function() {
					var fname = $(this).attr("name") == undefined ? $(this).attr("comboname") : $(this).attr("name");
					if (typeof (data[fname]) == "number") {
						data[fname] = new Date(parseInt(data[fname])).format("yyyy-MM-dd hh:mm:ss");
					}
				});
//				if(form.data('isFirst') == 0) {
					$.each(form.find('.easyui-datebox'), function() {
						var fname = $(this).attr("name") == undefined ? $(this).attr("comboname") : $(this).attr("name");
						if (typeof (data[fname]) == "number") {
							data[fname] = new Date(parseInt(data[fname])).format("yyyy-MM-dd");
						}
					});
//				}
			form.form('load', data);
//			$.each(form.find('.easyui-datebox'),
//					function() {
//						var value = $(this).val();
//						if (form.data('isFirst') == 0) {
//							value = $(this).datebox('getTsValue');
//							if (typeof(value)=="number") {
//								$(this).datebox('setValue',
//										new Date(value).format("yyyy-MM-dd"));
//							}
//						} else {
//							if (typeof(value)=="number") {
//								$(this).val(
//										new Date(parseInt(value))
//												.format("yyyy-MM-dd"));
//							}
//						}
//					});
//			$.each(form.find('.easyui-datetimebox'), function() {
//				var value = $(this).val();
//				if (form.data('isFirst') == 0) {
//					value = $(this).datetimebox('getValue');
//					if (typeof(value)=="number") {
//						$(this).datetimebox('setValue',
//								new Date(value).format("yyyy-MM-dd hh:mm:ss"));
//					}
//				} else {
//					if (typeof(value)=="number") {
//						$(this).val(
//								new Date(value).format("yyyy-MM-dd hh:mm:ss"));
//					}
//				}
//			});
			$.each(form.find('.easyui-combogrid'), function() {
				var value = $(this).combogrid('getValue');
				if (value === '')
					return true;
				var opts = $(this).combogrid('options');
				var isPaging = opts.pagination;
				if (!isPaging)
					return true;
				var isValid = opts.isValid === undefined ? true : opts.isValid;
				if (!isValid)
					return true;
				var selections = $(this).combogrid('grid').datagrid(
						'getSelections');
				if (selections == null || selections.length == 0) { // 娌℃湁瀵瑰簲琛�
                    var _this = $(this);
                    var valueField = opts.idField;
                    var textField = opts.textField;
                    var opts = _this.combogrid('grid').datagrid("options");
                    /*var params = new HdEzuiQueryParamsBuilder();
                    params.setHdEzuiQueryParams($.extend(true, {},
                        opts.queryParams));*/
                    //灏佽涓� hdquery  锛宻etAndClause澶辨晥
                    //params.setAndClause(valueField, value, '=', 'and');
                    var v_params = new HdQuery();
                    //key, rel, value, conn, type
                    v_params.addHq(valueField, '=', value, 'and');
                    v_params = v_params.build();
                    v_params.queryColumns = opts.queryParams.queryColumns;
                    $.ajax({
                        type: "post",
                        url: opts.url,
                        data: $.toJSON(v_params),
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data) {
                            if (data.rows.length > 0) {
                                _this.combogrid('setValue',
                                    data.rows[0][valueField]);
                                _this.combogrid('setText',
                                    data.rows[0][textField]);
                            }
                        },
                        error: function () {
                        }
                    });
                }
			});
		},
		/**
		 * 鑾峰緱FORM鏁版嵁锛屽悎骞堕檮鍔犳暟鎹紝浠SON涓茶繑鍥烇紝鐢ㄤ簬AJAX鎻愪氦鍚庡彴銆� HCB:
		 * 澧炲姞params(Object閿�煎):鐢ㄤ簬鎻愪氦鏃舵坊鍔犵殑鍏跺畠鍙傛暟 FXQ:
		 * 閲嶆瀯锛屽垎绂籫etDataAsObject鏂规硶杩斿洖瀵硅薄锛屾湰鏂规硶鍩轰簬姝ゆ柟娉曞疄鐜�
		 * 
		 * @param {ezuiObject}
		 *            form 閫夋嫨鍣ㄩ�変腑鐨� easyUI瀵硅薄,鏁版嵁宸茬粡鍒濆鍖�
		 * @param {object}
		 *            params 闄勫姞鍙傛暟锛岀敤浜庝竴骞舵彁浜ゅ埌鍚庡彴
		 * @return {object} FORM鏁版嵁鍐呭锛岄敭鍊煎瀵瑰簲浜嶧ORM鍚勪釜瀛楁
		 */
		getData : function(form, params) {
			var formObj = $.hd.form.getDataAsObject(form);
			if (!params || !$.isPlainObject(params)) {
				params = {}; // FXQ 淇濇寔鍏煎锛岄槻鑼僽ndefined
			}
			$.each(params, function(name, value) {
				if (formObj[name]) {
					formObj[name] = formObj[name] + "," + value;
				} else {
					formObj[name] = value;
				}
			});
			return $.toJSON(formObj);
		},
		/**
		 * 鑾峰緱FORM鏁版嵁锛屼互瀵硅薄鏍煎紡杩斿洖銆�
		 * 
		 * @param {ezuiObject}
		 *            form 閫夋嫨鍣ㄩ�変腑鐨� easyUI瀵硅薄,鏁版嵁宸茬粡鍒濆鍖�
		 * @return {object} FORM鏁版嵁鍐呭锛岄敭鍊煎瀵瑰簲浜嶧ORM鍚勪釜瀛楁
		 */
		getDataAsObject : function(form) {
			// 鎵撳紑鎵�鏈塪isabled椤�
//			var temp = form.find('input[disabled="disabled"]');
//			$.each(temp, function() {
//				$(this).removeAttr("disabled");
//			});
			// datebox鍊艰浆鎴恉atetimebox鍊�
//			var temp2 = form.find('.easyui-datebox');

			/*
			 * datetimebox 杞崲鎴愭爣鍑嗘牸寮忋�� @author jason
			 */
//			var temp4 = form.find('.easyui-datetimebox');
//			$.each(temp4, function() {
//				var value = $(this).datetimebox('getTsValue');
////				if (value != null) {
////					var howmany = value.split(":").length;
////					if (howmany == 2) {
////						$($(this).next().children().get(2)).val(value + ":00");
////					}
////				}
//			});
			var formObj = serializeObject(form,form.find('.easyui-datebox'),form.find('.easyui-datetimebox'));
			// serializeObject鏀逛负涓嶅澶栧叕寮�鐨勫嚱鏁�
			// 娣诲姞浣嶉�変腑鐨刢heckbox
			// 淇敼閫変腑鏂瑰紡涓簆rop,鍏煎鎬уソ,鍚屾椂澧炲姞閫変腑鏃朵负1鐨刬f鍒嗘敮
			// auth by zz
			var temp3 = form.find('input[type="checkbox"]');
			$.each(temp3, function() {
				if ($(this).prop('checked') == true) {
					formObj[this.name] = "1";
				} else {
					formObj[this.name] = "0";
				}
			});

			// [MARK]鑷虫锛宖ormObj鍚勫彉閲忚祴鍊煎畬姣�

			// 鏈柟娉曡繑鍥濷BJECT, 鐢╣etData杩斿洖JSON涓层�� var data = $.toJSON(formObj);

//			$.each(temp, function() { // 鎭㈠disabled椤�
//				$(this).attr("disabled", "disabled");
//			});

//			$.each(temp2,
//					function() { // 鎭㈠datebox鍊�
//						var value = $(this).datebox('getValue');
//						if (value !== '') {
//							$(this).datebox('setValue',
//									value.replace(' 00:00:00', ''));
//						}
//					});
			return formObj;

			function serializeObject(form,dt,dtt) {
				var o = {};
				var data = form.serializeArray();

				$.each(data, function() {
					var name = this['name'];
					var value = this['value'];
					if (o[name]) {
						o[name] = o[name] + "," + value;
					} else {
						o[name] = value;
					}
				});
				// 鏀瑰彉datebox鍊笺��
				$.each(dt, function() {
					var fname = $(this).attr("name") == undefined ? $(this).attr("comboname") : $(this).attr("name");
						o[fname] = new Date(o[fname]).getTime();
				});
				// 鏀瑰彉datetimebox鍊笺��
				$.each(dtt, function() {
					var fname = $(this).attr("name") == undefined ? $(this).attr("comboname") : $(this).attr("name");
						o[fname] = new Date(o[fname]).getTime();
				});
				return o;
			}
		}
	}
});