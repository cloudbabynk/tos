package net.huadong.tech.damage.service.impl;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import net.huadong.idev.hdmessagecode.HdMessageCode;
import net.huadong.tech.base.bean.HdQuery;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.base.entity.CBrand;
import net.huadong.tech.base.entity.CCarKind;
import net.huadong.tech.base.entity.CCarTyp;
import net.huadong.tech.base.entity.VWlBillVehicle;
import net.huadong.tech.damage.entity.MOverShortBack;
import net.huadong.tech.damage.service.MOverShortBackService;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.dao.QueryParamLs;
import net.huadong.tech.ship.entity.CShipData;
import net.huadong.tech.ship.entity.Ship;
import net.huadong.tech.shipbill.entity.BillCar;
import net.huadong.tech.shipbill.entity.BillSplit;
import net.huadong.tech.shipbill.entity.PortCar;
import net.huadong.tech.shipbill.entity.ShipBill;
import net.huadong.tech.springboot.core.HdEzuiDatagridData;
import net.huadong.tech.springboot.core.HdEzuiSaveDatagridData;
import net.huadong.tech.util.HdUtils;

/**
 * @author 
 */
@Component
public class MOverShortBackServiceImpl implements MOverShortBackService {
	@Override
	public HdEzuiDatagridData find(HdQuery hdQuery) {
		// TODO Auto-generated method stub
		String jpql="select a from MOverShortBack a where 1=1 ";
		String shipNo = hdQuery.getStr("shipNo");
		String billNo = hdQuery.getStr("billNo");
		//String recTim = hdQuery.getStr("recTim");
		//String portCarNo = hdQuery.getStr("portCarNo");
		String vinNo = hdQuery.getStr("vinNo");
		String rfidCardNo = hdQuery.getStr("rfidCardNo");
		String missId = hdQuery.getStr("missId");
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(shipNo)){
			jpql += "and a.shipNo =:shipNo ";
			paramLs.addParam("shipNo", shipNo);
		}
		if(HdUtils.strNotNull(billNo)){
			jpql += "and a.billNo =:billNo ";
			paramLs.addParam("billNo", billNo);
		}
		if(HdUtils.strNotNull(vinNo)){
			jpql += "and a.vinNo =:vinNo ";
			paramLs.addParam("vinNo", vinNo);
		}
		if(HdUtils.strNotNull(rfidCardNo)){
			jpql += "and a.rfidCardNo =:rfidCardNo ";
			paramLs.addParam("rfidCardNo", rfidCardNo);
		}
		if(HdUtils.strNotNull(missId)){
			jpql += "and a.missId =:missId ";
			paramLs.addParam("missId", missId);
		}
//		if(HdUtils.strNotNull(recTim)){
//			SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd" );
//			Date date = null;
//			try {
//				date= (Date) sdf.parse(recTim);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			jpql += "and a.recTim =:date ";
//			paramLs.addParam("date", date);
//		}
		jpql += "order by a.recTim desc";
		HdEzuiDatagridData results= JpaUtils.findByEz(jpql, paramLs , hdQuery);
		List<MOverShortBack> mOverShortBackList = results.getRows();
		for(MOverShortBack mOverShortBack : mOverShortBackList){
			//mOverShortBack.setiEId();
			//mOverShortBack.setMissId();
			if(HdUtils.strNotNull(mOverShortBack.getBrandCod())){
			mOverShortBack.setBrandCod(JpaUtils.findById(CBrand.class, mOverShortBack.getBrandCod()).getBrandNam());
			}
			}
		return results;
	}
	@Override
	public HdMessageCode save(HdEzuiSaveDatagridData<MOverShortBack> hdEzuiSaveDatagridData) {
		// TODO Auto-generated method stub
	return JpaUtils.save(hdEzuiSaveDatagridData);
	}
	@Transactional
	public void removeAll(String overshortIds) {
		List<String> overshortIdList = HdUtils.paraseStrs(overshortIds);
		for (String overshortId : overshortIdList) {
			if (checkExist(overshortId)) {
			throw new HdRunTimeException("舱单车辆信息存在！不能删除！");
			}
			JpaUtils.remove(MOverShortBack.class, overshortId);
		}	
	}
	//检验是否在场

		public boolean checkExist(String overshortId) {
			MOverShortBack mOverShortBack=JpaUtils.findById(MOverShortBack.class, overshortId);
			String jpql="select a from BillCar a where a.portCarNo=:portCarNo";
			QueryParamLs paramLs=new QueryParamLs();
			paramLs.addParam("portCarNo",mOverShortBack.getPortCarNo());
			List<BillCar> billCarList=JpaUtils.findAll(jpql, paramLs);
		    if(billCarList.size()>0){
		    	return true;
		    }
			return false;
		}
	@Override
	public MOverShortBack findone(String overshortId) {
		// TODO Auto-generated method stub
		MOverShortBack mOverShortBack = JpaUtils.findById(MOverShortBack.class, overshortId);
		return mOverShortBack;

	}

	@Override
	public HdMessageCode saveone(@RequestBody MOverShortBack mOverShortBack) {
		String overshortId = mOverShortBack.getOvershortId();
		MOverShortBack movershortback = JpaUtils.findById(MOverShortBack.class, overshortId);
		if(movershortback!=null){
			JpaUtils.update(mOverShortBack);	
		}else{
		mOverShortBack.setOvershortId(HdUtils.genUuid());
			JpaUtils.save(mOverShortBack);}
		return HdUtils.genMsg();
	}
	@Override
	public HdEzuiDatagridData findBillCar(HdQuery hdQuery) {
			String jpql="select a from BillCar a where 1=1 ";
			String shipNo = hdQuery.getStr("shipNo");
			String vinNo = hdQuery.getStr("vinNo");
			String billNo = hdQuery.getStr("billNo");
			String rfidCardNo = hdQuery.getStr("rfidCardNo");
			String brandCod = hdQuery.getStr("brandCod");
			String portCarNo = hdQuery.getStr("portCarNo");
			String carTyp = hdQuery.getStr("carTyp");
			QueryParamLs paramLs = new QueryParamLs();
			if(HdUtils.strNotNull(shipNo)){
				jpql += "and a.shipNo =:shipNo ";
				paramLs.addParam("shipNo", shipNo);
			}
			if(HdUtils.strNotNull(portCarNo)){
				jpql += "and a.portCarNo =:portCarNo ";
				paramLs.addParam("portCarNo", portCarNo);
			}
			if(HdUtils.strNotNull(vinNo)){
				jpql += "and a.vinNo =:vinNo ";
				paramLs.addParam("vinNo", vinNo);
			}
			if(HdUtils.strNotNull(billNo)){
				jpql += "and a.billNo =:billNo ";
				paramLs.addParam("billNo", billNo);
			}
			if(HdUtils.strNotNull(rfidCardNo)){
				jpql += "and a.rfidCardNo =:rfidCardNo ";
				paramLs.addParam("rfidCardNo", rfidCardNo);
			}
			if(HdUtils.strNotNull(brandCod)){
				jpql += "and a.brandCod =:brandCod ";
				paramLs.addParam("brandCod", brandCod);
			}
			if(HdUtils.strNotNull(carTyp)){
				jpql += "and a.carTyp =:carTyp ";
				paramLs.addParam("carTyp", carTyp);
			}
			jpql += "order by a.recTim desc";
			HdEzuiDatagridData result = JpaUtils.findByEz(jpql, paramLs, hdQuery);
			List<BillCar> billCarList = result.getRows();
			if(billCarList.size()>0){
				for(BillCar bc:billCarList){
					if (HdUtils.strNotNull(bc.getCarTyp())) {
						CCarTyp ccartyp = JpaUtils.findById(CCarTyp.class, bc.getCarTyp());
						bc.setCarTypNam(ccartyp.getCarTypNam());
					}
					if (HdUtils.strNotNull(bc.getCarKind())) {
						CCarKind carkind = JpaUtils.findById(CCarKind.class, bc.getCarKind());
						bc.setCarKindNam(carkind.getCarKindNam());
					}
					if (HdUtils.strNotNull(bc.getBrandCod())) {
						CBrand cbrand = JpaUtils.findById(CBrand.class, bc.getBrandCod());
						if (cbrand != null)
							bc.setBrandNam(cbrand.getBrandNam());
					}	
				}
			}
			return result;
		}
	@Override
	public HdMessageCode genmovershort(String shipNo, String tradeId, String billNo, String iEId, String vinNo,
			String missId) {
		//校验溢卸车辆的提单号是否存在
		String flag=checkVIN(vinNo,shipNo);
		if("notExist".equals(flag)){
		//生成溢卸车辆提单信息
			ShipBill sb=new ShipBill();
			String uuid=HdUtils.genUuid();
			ShipBill shipbill=JpaUtils.findById(ShipBill.class, uuid);
			if(shipbill!=null){
				throw new HdRunTimeException("提单已存在！");		
			}else{
			sb.setShipbillId(HdUtils.generateUUID());
			}
			sb.setTradeId(tradeId);
			sb.setiEId(iEId);
			sb.setShipNo(shipNo);
			sb.setBillNo(billNo);
			sb.setRecTim(HdUtils.getDateTime());
			sb.setRecNam(HdUtils.getCurUser().getAccount());
			JpaUtils.save(sb);
			BillSplit bs=new BillSplit();
			bs.setShipbillId(sb.getShipbillId());
			bs.setBillspId(HdUtils.genUuid());
			bs.setBillNo(billNo);
			bs.setTradeId(tradeId);
			bs.setiEId(iEId);
			bs.setShipNo(shipNo);
			bs.setRecTim(HdUtils.getDateTime());
			bs.setRecNam(HdUtils.getCurUser().getAccount());
			JpaUtils.save(bs);
		    BillCar billCar=new BillCar();
		    billCar.setBillcarId(HdUtils.generateUUID());
		    billCar.setShipbillId(sb.getShipbillId());
		    billCar.setTradeId(tradeId);
		    billCar.setShipNo(shipNo);
		    billCar.setiEId(iEId);
		    billCar.setBillNo(billNo);
		    billCar.setRecTim(HdUtils.getDateTime());
		    billCar.setRecNam(HdUtils.getCurUser().getAccount());
		    JpaUtils.save(billCar);
		    PortCar portCar =new PortCar();
		    portCar.setPortCarNo(billCar.getPortCarNo());
		    portCar.setVinNo(vinNo);
		    portCar.setCurrentStat("2");
		    portCar.setShipNo(shipNo);
		    portCar.setBillNo(billNo);
		    portCar.setTradeId(tradeId);
		    portCar.setiEId(iEId);
		    portCar.setInPortNo(" ");
		    Ship s=JpaUtils.findById(Ship.class, shipNo);
		    portCar.setDockCod(s.getDockCod());
		    JpaUtils.save(portCar);
		    MOverShortBack mOverShortBack=new MOverShortBack();
		    mOverShortBack.setOvershortId(HdUtils.genUuid());
		    mOverShortBack.setShipNo(shipNo);
		    mOverShortBack.setBillNo(billNo);
		    mOverShortBack.setiEId(iEId);
		    mOverShortBack.setPortCarNo(billCar.getPortCarNo());
		    mOverShortBack.setVinNo(vinNo);
		    mOverShortBack.setMissId(missId);
		    JpaUtils.save(mOverShortBack);
		}
		return HdUtils.genMsg();
	}
	
	public String checkVIN(String vin, String shipNo) {
		String result = null;
		String imo = "";
		String billNo=""; 
		if(HdUtils.strNotNull(shipNo)){
			Ship ship = JpaUtils.findById(Ship.class, shipNo);
            if(ship != null){
            	CShipData cShipData = JpaUtils.findById(CShipData.class, ship.getShipCodId());
            	imo = cShipData.getShipImo();
            }
		}
		String jpql = "select a from VWlBillVehicle a where 1=1 ";
		QueryParamLs paramLs = new QueryParamLs();
		if(HdUtils.strNotNull(vin)){
			jpql += "and a.vin like :vin ";
			paramLs.addParam("vin", "%" + vin );
		}
		if(HdUtils.strNotNull(imo)){
			jpql += "and a.imo =:imo ";
			paramLs.addParam("imo", imo );
		}
		List<VWlBillVehicle> vWlBillVehicleList = JpaUtils.findAll(jpql, paramLs);
		if(vWlBillVehicleList.size()>0){
			VWlBillVehicle vWlBillVehicle = vWlBillVehicleList.get(0);
		 billNo =vWlBillVehicle.getBillNo();
		}
		String jpqlc="select a from ShipBill a where a.billNo=:billNo ";
		QueryParamLs paramLc = new QueryParamLs();
		paramLc.addParam("billNo",billNo);
		List<ShipBill> shipbillL=JpaUtils.findAll(jpqlc, paramLc);
		if(shipbillL.size()>0){
		throw new HdRunTimeException("提单存在,请选择！");	
		}
		else{
			result="notExist";	
		}
		return result;
	}
}

