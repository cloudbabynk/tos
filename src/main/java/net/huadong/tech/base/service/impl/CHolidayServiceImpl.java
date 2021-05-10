package net.huadong.tech.base.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CHoliday;
import net.huadong.tech.base.service.CHolidayService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

@Component
public class CHolidayServiceImpl implements CHolidayService {

	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql = "select a from CHoliday a where 1=1 ";
		String hId = hdQuery.getStr("hId");
		String holidayYear = hdQuery.getStr("yearChoose");
		String holidayMonth = hdQuery.getStr("monthChoose");
		QueryParamLs paramLs = new QueryParamLs();
		if (HdUtils.strNotNull(hId)) {
			jpql += "and a.hId =:hId ";
			paramLs.addParam("hId", hId);
		}
		if (HdUtils.strNotNull(holidayYear)) {
			jpql += "and a.holidayYear =:holidayYear ";
			paramLs.addParam("holidayYear", holidayYear);
		}
		if (HdUtils.strNotNull(holidayMonth)) {
			int M= Integer.parseInt(holidayMonth);
			if(M<10){
			jpql += "and a.holidayMonth =:holidayMonth ";
			paramLs.addParam("holidayMonth", 0+holidayMonth);
			}else{
				jpql += "and a.holidayMonth =:holidayMonth ";
				paramLs.addParam("holidayMonth",holidayMonth);	
			}
		}
		jpql += " order by a.actDate asc";
		return JpaUtils.findByEz(jpql, paramLs, hdQuery);
	}

	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<CHoliday> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
		return JpaUtils.save(hdEzuiSaveDatagridData);
	}

	@Transactional
	public void removeAll(String hIds) {
		// TODO Auto-generated method stub
		List<String> hIdList = HdUtils.paraseStrs(hIds);
		for (String hId : hIdList) {
			JpaUtils.remove(CHoliday.class, hId);
		}
	}

	@Override
	public CHoliday findone(String hId) {
		// TODO Auto-generated method stub
		CHoliday cHoliday = JpaUtils.findById(CHoliday.class, hId);
		return cHoliday;

	}

	@Override
	public HdMessageCode saveone(@RequestBody CHoliday cHoliday) {
		// TODO Auto-generated method stub
		String hId = cHoliday.gethId();
		CHoliday choliday = JpaUtils.findById(CHoliday.class, hId);
		if (choliday != null) {
			JpaUtils.update(cHoliday);
		} else {
			String uuid = UUID.randomUUID().toString();
			CHoliday ch = JpaUtils.findById(CHoliday.class, uuid);
			if (ch != null) {
				throw new HdRunTimeException("该代码已存在，请重新输入！");// 主键已存在
			} else {
				cHoliday.sethId(uuid);
				JpaUtils.save(cHoliday);
			}
		}
		return HdUtils.genMsg();
	}

	@Override
	public void initHoliday(String year) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		java.util.Date start = null;
		try {
			start = sdf.parse(year + "-01-01");
		} catch (ParseException e) {
			e.printStackTrace();
		} // 开始时间
		java.util.Date end = null;
		try {
			end = sdf.parse(year + "-12-31");
		} catch (ParseException e) {
			e.printStackTrace();
		} // 结束时间
		try {
			List<Date> lists = dateSplit(start, end);
            System.out.println(lists);
  		  //---------------插入节假日时间------------------
            List<CHoliday> holidays = new ArrayList<CHoliday>();
            //元旦
            Date yd=new Date();
            yd=sdf.parse(year+"-01-01");
            SimpleDateFormat sdw = new SimpleDateFormat("E");  
           String week= sdw.format(yd);
            holidays.add(new CHoliday(HdUtils.genUuid(),year,"01","01",week,"1","0", yd,HdUtils.getCurUser().getAccount(),HdUtils.getDateTime()));
            //春节
//            Date cj=new Date();
//            cj=HdUtils.getDateTime();
//            holidays.add(new CHoliday(HdUtils.genUuid(),year,"1","1","1","1","0", sdf.parse("2018-02-15"),HdUtils.getCurUser().getAccount(),cj));
//            holidays.add(new CHoliday(HdUtils.genUuid(),year,"1","1","1","1","0", sdf.parse("2018-02-16"),HdUtils.getCurUser().getAccount(),cj));
//            holidays.add(new CHoliday(HdUtils.genUuid(),year,"1","1","1","1","0", sdf.parse("2018-02-17"),HdUtils.getCurUser().getAccount(),cj));
//           //清明
//            Date qm=new Date();
//            qm=HdUtils.getDateTime();
//            holidays.add(new CHoliday(HdUtils.genUuid(),year,"1","1","1","1","0", sdf.parse("2018-04-05"),HdUtils.getCurUser().getAccount(),qm));
            //劳动
            Date ld=new Date();
            ld=sdf.parse(year+"-05-01");
            holidays.add(new CHoliday(HdUtils.genUuid(),year,"05","01","1","1","0",ld,HdUtils.getCurUser().getAccount(),HdUtils.getDateTime()));
//           //端午
//            Date dw=new Date();
//            dw=HdUtils.getDateTime();
//            holidays.add(new CHoliday(HdUtils.genUuid(),year,"1","1","1","1","0", sdf.parse("2018-06-18"),HdUtils.getCurUser().getAccount(),dw));
           //中秋
//            Date zq=new Date();
//            zq=HdUtils.getDateTime();
//            holidays.add(new CHoliday(HdUtils.genUuid(),year,"1","1","1","1","0", sdf.parse("2018-09-24"),HdUtils.getCurUser().getAccount(),zq));
//           //国庆
            Date gq=new Date();
            gq=sdf.parse(year+"-10-01");
            holidays.add(new CHoliday(HdUtils.genUuid(),year,"10","01","1","1","0",gq,HdUtils.getCurUser().getAccount(),HdUtils.getDateTime()));
            holidays.add(new CHoliday(HdUtils.genUuid(),year,"10","01","1","1","0",gq,HdUtils.getCurUser().getAccount(),HdUtils.getDateTime()));
            holidays.add(new CHoliday(HdUtils.genUuid(),year,"10","01","1","1","0",gq,HdUtils.getCurUser().getAccount(),HdUtils.getDateTime()));
           for(CHoliday choliday:holidays){
        	  choliday.setHolidayId("1");
        	  String jpql = " select a from CHoliday a where a.actDate=:actDate ";
				QueryParamLs paramLs = new QueryParamLs();
				paramLs.addParam("actDate", choliday.getActDate());
				List<CHoliday> cholidayList = JpaUtils.findAll(jpql, paramLs);
				  JpaUtils.save(choliday);
           } 
			// -------------------插入周末时间---------------
			if (!lists.isEmpty()) {
				for (Date date : lists) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
						System.out.println("插入日期:" + sdf.format(date) + ",周末");
						String jpql = " select a from CHoliday a where a.actDate=:actDate ";
						QueryParamLs paramLs = new QueryParamLs();
						paramLs.addParam("actDate", date);
						List<CHoliday> cholidayList = JpaUtils.findAll(jpql, paramLs);
						if (cholidayList.size() > 0) {
							return;
						} else {
							String dte = sdf.format(date);
							String y = dte.substring(0, 4);
							String m = dte.substring(5, 7);
							String d = dte.substring(8, 10);
							CHoliday choliday = new CHoliday();
							choliday.sethId(HdUtils.genUuid());
							choliday.setHolidayYear(y);
							choliday.setHolidayMonth(m);
							choliday.setHolidayDay(d);
							choliday.setHolidayWeek("6");
							choliday.setWeekendId("1");
							choliday.setHolidayId("0");
							choliday.setActDate(sdf.parse(sdf.format(date)));
							choliday.setRecNam(HdUtils.getCurUser().getAccount());
							choliday.setRecTim(HdUtils.getDateTime());
							JpaUtils.save(choliday);
						}

					}
				if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
						String jpql = " select a from CHoliday a where a.actDate=:actDate ";
						QueryParamLs paramLs = new QueryParamLs();
						paramLs.addParam("actDate", date);
						List<CHoliday> cholidayList = JpaUtils.findAll(jpql, paramLs);
						if (cholidayList.size() > 0) {
							return;
						} else {
							String dte = sdf.format(date);
							String y = dte.substring(0, 4);
							String m = dte.substring(5, 7);
							String d = dte.substring(8, 10);
							CHoliday choliday = new CHoliday();
							choliday.sethId(HdUtils.genUuid());
							choliday.setHolidayYear(y);
							choliday.setHolidayMonth(m);
							choliday.setHolidayDay(d);
							choliday.setHolidayWeek("0");
							choliday.setWeekendId("1");
							choliday.setHolidayId("0");
							choliday.setActDate(sdf.parse(sdf.format(date)));
							choliday.setRecNam(HdUtils.getCurUser().getAccount());
							choliday.setRecTim(HdUtils.getDateTime());
							JpaUtils.save(choliday);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static List<Date> dateSplit(java.util.Date start, Date end) throws Exception {
		if (!start.before(end))
			throw new Exception("开始时间应该在结束时间之后");
		Long spi = end.getTime() - start.getTime();
		Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(end);
		for (int i = 1; i <= step; i++) {
			dateList.add(new Date(dateList.get(i - 1).getTime() - (24 * 60 * 60 * 1000)));// 比上一天减一
		}
		return dateList;
	}
}
