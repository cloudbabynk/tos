/*
 * Copyright (C) 2015 HUADONG SOFT-TECH CO.,LTD.
 * Author: xiaojn <xiaojn@huadong.net>
 */
package net.huadong.tech.privilege.entity;

/**
 * 系统管理-用户实体帮助
 *
 * @author xiaojn
 * @version 1.0.0
 * @since 2015-4-7 16:51:00
 */
public class AuthUserHelper {

	/**
	 * 转为不带密码的对象
	 * 
	 * @param user
	 * @return
	 */
	public static AuthUserNoPwd ridPassword(AuthUser user) {
		AuthUserNoPwd u = new AuthUserNoPwd();
		u.setUserId(user.getUserId());
		u.setAccount(user.getAccount());
		u.setAccountPassDate(user.getAccountPassDate());
		u.setLockId(user.getLockId());
		u.setStopId(user.getStopId());
		u.setName(user.getName());
		u.setPwNeverPassId(user.getPwNeverPassId());
		u.setPwValidDays(user.getPwValidDays());
		u.setPwModifyTim(user.getPwModifyTim());
		u.setDonotChgId(user.getDonotChgId());
		u.setNextChgId(user.getNextChgId());
		u.setSkin(user.getSkin());
		u.setRecNam(user.getRecNam());
		u.setRecTim(user.getRecTim());
		u.setUpdNam(user.getUpdNam());
		u.setUpdTim(user.getUpdTim());
		u.setDescription(user.getDescription());
		//u.setTenantId(user.getTenantId());// shishioktazai
		//u.setOrgnId(user.getOrgnId());
		u.setCompanyCod(user.getCompanyCod());
		return u;
	}
}
