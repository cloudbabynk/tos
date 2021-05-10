
function HdQuery() {// 查询HdEzuiQueryParamsBuilder的封装简化代码
	var highQuery = {};
	highQuery.adQueryLs = [];
	this.hdQueryBuilder = new HdEzuiQueryParamsBuilder();
	this.getHighQuery = function() {
		return highQuery;
	}
	this.add = function(key, value) {
		this.hdQueryBuilder.setOtherskeyValue(key, value);
	}
	this.setOrderByClause = function(name, order) {
		this.hdQueryBuilder.setOrderByClause(name, order);
	}
	this.setAndClause = function(column, value, operation, conjunction) {
		this.hdQueryBuilder.setAndClause(column, value, operation, conjunction);
	}
	this.set = function(name, order) {
		this.hdQueryBuilder.set(name, order);
	}
	this.addHq = function(key, rel, value, conn, type) {
		var oneObj = {
		    key : key,
		    rel : rel,
		    value : value,
		    conn : conn,
		    type : type
		};
		highQuery.adQueryLs.push(oneObj);
	}
	this.build = function() {
		$.extend(highQuery, this.hdQueryBuilder.build());
		return highQuery;
	}
}
