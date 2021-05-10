/* 
 * Copyright (C) 2016 HuaDong CO.,LTD.
 * Author:jason <caiyj@huadong.net>
 */

$.extend($.fn.datebox.methods, {
	/**
	 * 璁剧疆鏃堕棿鎴炽��
	 * 
	 * @function
	 * @param string
	 *            ts 鏃堕棿鎴炽��
	 * @name datebox#setTsValue
	 */
	setTsValue : function(jq, ts) {
		return jq.each(function() {
			// TODO 闇�瑕佹挵鍐欏浗闄呭寲鏍煎紡銆�
			$(this).datebox("setValue", new Date(ts).format("yyyy-MM-dd"));
		});
	},
	/**
	 * 鑾峰彇鏃堕棿鎴炽��
	 * 
	 * @function
	 * @name datebox#getTsValue
	 */
	getTsValue : function(jq) {
		var ret = -1;
		jq.each(function() {
			ret = new Date($(this).datebox("getValue")).getTime();
		});
		return ret;
	}
});