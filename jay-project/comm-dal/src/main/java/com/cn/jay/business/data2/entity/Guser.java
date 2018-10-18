package com.cn.jay.business.data2.entity;

import java.io.Serializable;
import java.util.Date;

/**
TABLE:.g_user                   
--------------------------------------------------------
userId               Long(19)           NOTNULL             //用户ID
userName             String(48)                             //用户名
headUrl              String(256)                            //头像URL
state                Integer(10)                 0          //用户状态(0位为正常位 1位禁言位 2位封号位)
costMoney            Long(19)                    0          //消耗的货币数量
money                Long(19)                    0          //普通货币数量
diamond              Integer(10)                 0          //钻石数量
exp                  Long(19)                    0          //用户经验
vipExp               Integer(10)                 0          //vip经验
amount               Integer(10)                 0          //充值金额,单位1Q点
actDay               Integer(10)                 0          //活跃天数
towerCodeId          Integer(10)                            //炮台ID
towerRateId          Integer(10)                            //炮台位率ID
towerSeatId          Integer(10)                            //炮座ID
towerFuncId          Integer(10)                 0          //功能炮ID
skillBulletNum       Integer(10)                 0          //大招子弹累计数
lastRecTime          Date(19)           NOTNULL             //上一次充值时间
createTime           Date(19)                               //帐号创建时间
loginTime            Date(19)                               //最近一次登录时间
logoutTime           Date(19)                               //最近一次登出时间
addLogin             Integer(10)                 1          //累计登录天数
cardGainCount        Integer(10)                 0          //当天收卡牌礼盒次数
giftGiveCount        Integer(10)                 0          //当天送金币礼盒次数
giftGainCount        Integer(10)                 0          //当天收金币礼盒次数
sevenBox             String(20)         NOTNULL             //领取的七天任务礼盒, 逗号分隔
introduction         String(512)                            //个人简介
specialGunCount      Integer(10)                 0          //使用功能炮次数
dropCardRefresh      Byte(3)                     0          //卡牌掉落公共库存每日刷新
payBackRefresh       Byte(3)                     0          //返奖公共库存每日登陆奖励刷新
cardDemandCount      Integer(10)                 0          //当天卡牌索要次数
cardGiveCount        Integer(10)                 0          //当天卡牌赠送次数
isNew                Integer(10)                 0          //0 新号  1 非新号
smallGameId          Integer(10)                 0          //0 无小游戏 
smallGameMoney       Long(19)                    0          //小游戏金币
piggyBankMoney       Double(24,8)                0.00000000 //存钱罐金币
fishBookNodeId       String(2048)                           //关卡特色按钮是否已点击查看记录
firstRecharge        Integer(10)                 0          //0 未充值 1 已充值未领取 2 已领取
firstRechargeData    String(128)                            //首充数据,格式："7,1"，7表示充值表项目id，1表示充值时vip等级
firstRechargeTime    Date(19)                               //首充时间
secondRechargeTime   Date(19)                               //第二笔充值时间
thirdRechargeTime    Date(19)                               //第三笔充值时间
luckyGiftsTime       Date(19)                               //幸运礼包触发时间
luckyGiftsIsGet      Integer(10)                 0          //幸运礼包领取 0 未领取  1 已领取
luckyGiftsRe         Integer(10)                 0          //幸运礼包充值金额 单位 0.01元
dayGainMoney         Long(19)                    0          //每天收获的金币数
isYesterdayLogin     Integer(10)                 0          //玩家昨天是否登录过 0 没有  1 有
gunPower             Integer(10)                 100000     //炮倍
sevenDay             Integer(10)                 0          //七天登录奖励领取 作位运算
addOnlineTime        Integer(10)                 0          //累计在线时长 秒
onlineRd             Integer(10)                 0          //在线奖励领取奖励 作位运算
isLogin              Integer(10)                 1          //今天是否登录过，0:没有 1:已登录过
clubId               Long(19)                    0          //俱乐部id
clubName             String(128)                            //俱乐部名字
dayAct               Integer(10)                 0          //今日活跃
weekAct              Integer(10)                 0          //本周活跃
dayActBox            Integer(10)                 0          //今日活跃箱子领取 作位运算
weekActBox           Integer(10)                 0          //本周活跃箱子领取 作位运算
makeUp               Integer(10)                 0          //每日金币补足
catchMoneyTree       Integer(10)                 0          //捕获摇钱树次数
nodes                String(128)                            //进入过的节点ID
maxCatch             Long(19)                    0          //单次最大捕获
seaGodBill           Long(19)                    0          //海王币
isValidate           Integer(10)                 0          //0.为未验证 1.为未成人 2.为成人(主页防沉迷按钮 0显示  1、2隐藏)
onlineTime           Integer(10)                 0          //在线时长 毫秒
income               Integer(10)                 100        //收益
antiOutTime          Integer(10)                 0          //防沉迷零收益离线时长
recGradeValue        Long(19)                    0          //充值优质度(100万倍)
actGradeValue        Long(19)                    0          //活跃优质度(100万倍)
pearlFieldId         Integer(10)                 0          //珍珠夺宝当前倍场
cardScore            Integer(10)                 0          //卡牌积分
cardContorlTime      Date(19)                               //卡牌积分控制生效时间
carnivalGunPower     Integer(10)                 0          //狂欢Jackpot关卡当前使用的炮倍
prizeCardBulletNum   Integer(10)                 0          //大奖赛卡牌鱼子弹累积数
jackpotCardBulletNum Integer(10)                 0          //卡牌jackpot卡牌鱼子弹累积数
recPackBackBulletNum Integer(10)                 0          //充值返奖子弹累计数
discountGiftTime     Date(19)                               //特价礼包活动购买时间
discountGiftCount    Integer(10)                 0          //特价礼包已发邮件次数
hasDoGuide           Integer(10)                 0          //是否已处理新手引导 0为未处理，1为玩家已进行过引导，2为玩家选择是老玩家
ttgtBulletNum        Integer(10)                 0          //万炮宝藏子弹数
shellTag             Integer(10)                 0          //五彩贝壳(特殊鱼玩法)，每个玩家，首次开启规避0倍，0机会没使用 1已使用
*/
public class Guser implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private	Long userId;
	private	String userName;
	private	String headUrl;
	private	Integer state;
	private	Long costMoney;
	private	Long money;
	private	Integer diamond;
	private	Long exp;
	private	Integer vipExp;
	private	Integer amount;
	private	Integer actDay;
	private	Integer towerCodeId;
	private	Integer towerRateId;
	private	Integer towerSeatId;
	private	Integer towerFuncId;
	private	Integer skillBulletNum;
	private	Date lastRecTime;
	private	Date createTime;
	private	Date loginTime;
	private	Date logoutTime;
	private	Integer addLogin;
	private	Integer cardGainCount;
	private	Integer giftGiveCount;
	private	Integer giftGainCount;
	private	String sevenBox;
	private	String introduction;
	private	Integer specialGunCount;
	private	Byte dropCardRefresh;
	private	Byte payBackRefresh;
	private	Integer cardDemandCount;
	private	Integer cardGiveCount;
	private	Integer isNew;
	private	Integer smallGameId;
	private	Long smallGameMoney;
	private	Double piggyBankMoney;
	private	String fishBookNodeId;
	private	Integer firstRecharge;
	private	String firstRechargeData;
	private	Date firstRechargeTime;
	private	Date secondRechargeTime;
	private	Date thirdRechargeTime;
	private	Date luckyGiftsTime;
	private	Integer luckyGiftsIsGet;
	private	Integer luckyGiftsRe;
	private	Long dayGainMoney;
	private	Integer isYesterdayLogin;
	private	Integer gunPower;
	private	Integer sevenDay;
	private	Integer addOnlineTime;
	private	Integer onlineRd;
	private	Integer isLogin;
	private	Long clubId;
	private	String clubName;
	private	Integer dayAct;
	private	Integer weekAct;
	private	Integer dayActBox;
	private	Integer weekActBox;
	private	Integer makeUp;
	private	Integer catchMoneyTree;
	private	String nodes;
	private	Long maxCatch;
	private	Long seaGodBill;
	private	Integer isValidate;
	private	Integer onlineTime;
	private	Integer income;
	private	Integer antiOutTime;
	private	Long recGradeValue;
	private	Long actGradeValue;
	private	Integer pearlFieldId;
	private	Integer cardScore;
	private	Date cardContorlTime;
	private	Integer carnivalGunPower;
	private	Integer prizeCardBulletNum;
	private	Integer jackpotCardBulletNum;
	private	Integer recPackBackBulletNum;
	private	Date discountGiftTime;
	private	Integer discountGiftCount;
	private	Integer hasDoGuide;
	private	Integer ttgtBulletNum;
	private	Integer shellTag;

	/**
	* userId  Long(19)  NOTNULL  //用户ID    
	*/
	public Long getUserId(){
		return userId;
	}
	
	/**
	* userId  Long(19)  NOTNULL  //用户ID    
	*/
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	/**
	* userName  String(48)  //用户名    
	*/
	public String getUserName(){
		return userName;
	}
	
	/**
	* userName  String(48)  //用户名    
	*/
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	/**
	* headUrl  String(256)  //头像URL    
	*/
	public String getHeadUrl(){
		return headUrl;
	}
	
	/**
	* headUrl  String(256)  //头像URL    
	*/
	public void setHeadUrl(String headUrl){
		this.headUrl = headUrl;
	}
	
	/**
	* state  Integer(10)  0  //用户状态(0位为正常位  1位禁言位  2位封号位)    
	*/
	public Integer getState(){
		return state;
	}
	
	/**
	* state  Integer(10)  0  //用户状态(0位为正常位  1位禁言位  2位封号位)    
	*/
	public void setState(Integer state){
		this.state = state;
	}
	
	/**
	* costMoney  Long(19)  0  //消耗的货币数量    
	*/
	public Long getCostMoney(){
		return costMoney;
	}
	
	/**
	* costMoney  Long(19)  0  //消耗的货币数量    
	*/
	public void setCostMoney(Long costMoney){
		this.costMoney = costMoney;
	}
	
	/**
	* money  Long(19)  0  //普通货币数量    
	*/
	public Long getMoney(){
		return money;
	}
	
	/**
	* money  Long(19)  0  //普通货币数量    
	*/
	public void setMoney(Long money){
		this.money = money;
	}
	
	/**
	* diamond  Integer(10)  0  //钻石数量    
	*/
	public Integer getDiamond(){
		return diamond;
	}
	
	/**
	* diamond  Integer(10)  0  //钻石数量    
	*/
	public void setDiamond(Integer diamond){
		this.diamond = diamond;
	}
	
	/**
	* exp  Long(19)  0  //用户经验    
	*/
	public Long getExp(){
		return exp;
	}
	
	/**
	* exp  Long(19)  0  //用户经验    
	*/
	public void setExp(Long exp){
		this.exp = exp;
	}
	
	/**
	* vipExp  Integer(10)  0  //vip经验    
	*/
	public Integer getVipExp(){
		return vipExp;
	}
	
	/**
	* vipExp  Integer(10)  0  //vip经验    
	*/
	public void setVipExp(Integer vipExp){
		this.vipExp = vipExp;
	}
	
	/**
	* amount  Integer(10)  0  //充值金额,单位1Q点    
	*/
	public Integer getAmount(){
		return amount;
	}
	
	/**
	* amount  Integer(10)  0  //充值金额,单位1Q点    
	*/
	public void setAmount(Integer amount){
		this.amount = amount;
	}
	
	/**
	* actDay  Integer(10)  0  //活跃天数    
	*/
	public Integer getActDay(){
		return actDay;
	}
	
	/**
	* actDay  Integer(10)  0  //活跃天数    
	*/
	public void setActDay(Integer actDay){
		this.actDay = actDay;
	}
	
	/**
	* towerCodeId  Integer(10)  //炮台ID    
	*/
	public Integer getTowerCodeId(){
		return towerCodeId;
	}
	
	/**
	* towerCodeId  Integer(10)  //炮台ID    
	*/
	public void setTowerCodeId(Integer towerCodeId){
		this.towerCodeId = towerCodeId;
	}
	
	/**
	* towerRateId  Integer(10)  //炮台位率ID    
	*/
	public Integer getTowerRateId(){
		return towerRateId;
	}
	
	/**
	* towerRateId  Integer(10)  //炮台位率ID    
	*/
	public void setTowerRateId(Integer towerRateId){
		this.towerRateId = towerRateId;
	}
	
	/**
	* towerSeatId  Integer(10)  //炮座ID    
	*/
	public Integer getTowerSeatId(){
		return towerSeatId;
	}
	
	/**
	* towerSeatId  Integer(10)  //炮座ID    
	*/
	public void setTowerSeatId(Integer towerSeatId){
		this.towerSeatId = towerSeatId;
	}
	
	/**
	* towerFuncId  Integer(10)  0  //功能炮ID    
	*/
	public Integer getTowerFuncId(){
		return towerFuncId;
	}
	
	/**
	* towerFuncId  Integer(10)  0  //功能炮ID    
	*/
	public void setTowerFuncId(Integer towerFuncId){
		this.towerFuncId = towerFuncId;
	}
	
	/**
	* skillBulletNum  Integer(10)  0  //大招子弹累计数    
	*/
	public Integer getSkillBulletNum(){
		return skillBulletNum;
	}
	
	/**
	* skillBulletNum  Integer(10)  0  //大招子弹累计数    
	*/
	public void setSkillBulletNum(Integer skillBulletNum){
		this.skillBulletNum = skillBulletNum;
	}
	
	/**
	* lastRecTime  Date(19)  NOTNULL  //上一次充值时间    
	*/
	public Date getLastRecTime(){
		return lastRecTime;
	}
	
	/**
	* lastRecTime  Date(19)  NOTNULL  //上一次充值时间    
	*/
	public void setLastRecTime(Date lastRecTime){
		this.lastRecTime = lastRecTime;
	}
	
	/**
	* createTime  Date(19)  //帐号创建时间    
	*/
	public Date getCreateTime(){
		return createTime;
	}
	
	/**
	* createTime  Date(19)  //帐号创建时间    
	*/
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	/**
	* loginTime  Date(19)  //最近一次登录时间    
	*/
	public Date getLoginTime(){
		return loginTime;
	}
	
	/**
	* loginTime  Date(19)  //最近一次登录时间    
	*/
	public void setLoginTime(Date loginTime){
		this.loginTime = loginTime;
	}
	
	/**
	* logoutTime  Date(19)  //最近一次登出时间    
	*/
	public Date getLogoutTime(){
		return logoutTime;
	}
	
	/**
	* logoutTime  Date(19)  //最近一次登出时间    
	*/
	public void setLogoutTime(Date logoutTime){
		this.logoutTime = logoutTime;
	}
	
	/**
	* addLogin  Integer(10)  1  //累计登录天数    
	*/
	public Integer getAddLogin(){
		return addLogin;
	}
	
	/**
	* addLogin  Integer(10)  1  //累计登录天数    
	*/
	public void setAddLogin(Integer addLogin){
		this.addLogin = addLogin;
	}
	
	/**
	* cardGainCount  Integer(10)  0  //当天收卡牌礼盒次数    
	*/
	public Integer getCardGainCount(){
		return cardGainCount;
	}
	
	/**
	* cardGainCount  Integer(10)  0  //当天收卡牌礼盒次数    
	*/
	public void setCardGainCount(Integer cardGainCount){
		this.cardGainCount = cardGainCount;
	}
	
	/**
	* giftGiveCount  Integer(10)  0  //当天送金币礼盒次数    
	*/
	public Integer getGiftGiveCount(){
		return giftGiveCount;
	}
	
	/**
	* giftGiveCount  Integer(10)  0  //当天送金币礼盒次数    
	*/
	public void setGiftGiveCount(Integer giftGiveCount){
		this.giftGiveCount = giftGiveCount;
	}
	
	/**
	* giftGainCount  Integer(10)  0  //当天收金币礼盒次数    
	*/
	public Integer getGiftGainCount(){
		return giftGainCount;
	}
	
	/**
	* giftGainCount  Integer(10)  0  //当天收金币礼盒次数    
	*/
	public void setGiftGainCount(Integer giftGainCount){
		this.giftGainCount = giftGainCount;
	}
	
	/**
	* sevenBox  String(20)  NOTNULL  //领取的七天任务礼盒,  逗号分隔    
	*/
	public String getSevenBox(){
		return sevenBox;
	}
	
	/**
	* sevenBox  String(20)  NOTNULL  //领取的七天任务礼盒,  逗号分隔    
	*/
	public void setSevenBox(String sevenBox){
		this.sevenBox = sevenBox;
	}
	
	/**
	* introduction  String(512)  //个人简介    
	*/
	public String getIntroduction(){
		return introduction;
	}
	
	/**
	* introduction  String(512)  //个人简介    
	*/
	public void setIntroduction(String introduction){
		this.introduction = introduction;
	}
	
	/**
	* specialGunCount  Integer(10)  0  //使用功能炮次数    
	*/
	public Integer getSpecialGunCount(){
		return specialGunCount;
	}
	
	/**
	* specialGunCount  Integer(10)  0  //使用功能炮次数    
	*/
	public void setSpecialGunCount(Integer specialGunCount){
		this.specialGunCount = specialGunCount;
	}
	
	/**
	* dropCardRefresh  Byte(3)  0  //卡牌掉落公共库存每日刷新    
	*/
	public Byte getDropCardRefresh(){
		return dropCardRefresh;
	}
	
	/**
	* dropCardRefresh  Byte(3)  0  //卡牌掉落公共库存每日刷新    
	*/
	public void setDropCardRefresh(Byte dropCardRefresh){
		this.dropCardRefresh = dropCardRefresh;
	}
	
	/**
	* payBackRefresh  Byte(3)  0  //返奖公共库存每日登陆奖励刷新    
	*/
	public Byte getPayBackRefresh(){
		return payBackRefresh;
	}
	
	/**
	* payBackRefresh  Byte(3)  0  //返奖公共库存每日登陆奖励刷新    
	*/
	public void setPayBackRefresh(Byte payBackRefresh){
		this.payBackRefresh = payBackRefresh;
	}
	
	/**
	* cardDemandCount  Integer(10)  0  //当天卡牌索要次数    
	*/
	public Integer getCardDemandCount(){
		return cardDemandCount;
	}
	
	/**
	* cardDemandCount  Integer(10)  0  //当天卡牌索要次数    
	*/
	public void setCardDemandCount(Integer cardDemandCount){
		this.cardDemandCount = cardDemandCount;
	}
	
	/**
	* cardGiveCount  Integer(10)  0  //当天卡牌赠送次数    
	*/
	public Integer getCardGiveCount(){
		return cardGiveCount;
	}
	
	/**
	* cardGiveCount  Integer(10)  0  //当天卡牌赠送次数    
	*/
	public void setCardGiveCount(Integer cardGiveCount){
		this.cardGiveCount = cardGiveCount;
	}
	
	/**
	* isNew  Integer(10)  0  //0  新号  1  非新号    
	*/
	public Integer getIsNew(){
		return isNew;
	}
	
	/**
	* isNew  Integer(10)  0  //0  新号  1  非新号    
	*/
	public void setIsNew(Integer isNew){
		this.isNew = isNew;
	}
	
	/**
	* smallGameId  Integer(10)  0  //0  无小游戏    
	*/
	public Integer getSmallGameId(){
		return smallGameId;
	}
	
	/**
	* smallGameId  Integer(10)  0  //0  无小游戏    
	*/
	public void setSmallGameId(Integer smallGameId){
		this.smallGameId = smallGameId;
	}
	
	/**
	* smallGameMoney  Long(19)  0  //小游戏金币    
	*/
	public Long getSmallGameMoney(){
		return smallGameMoney;
	}
	
	/**
	* smallGameMoney  Long(19)  0  //小游戏金币    
	*/
	public void setSmallGameMoney(Long smallGameMoney){
		this.smallGameMoney = smallGameMoney;
	}
	
	/**
	* piggyBankMoney  Double(24,8)  0.00000000  //存钱罐金币    
	*/
	public Double getPiggyBankMoney(){
		return piggyBankMoney;
	}
	
	/**
	* piggyBankMoney  Double(24,8)  0.00000000  //存钱罐金币    
	*/
	public void setPiggyBankMoney(Double piggyBankMoney){
		this.piggyBankMoney = piggyBankMoney;
	}
	
	/**
	* fishBookNodeId  String(2048)  //关卡特色按钮是否已点击查看记录    
	*/
	public String getFishBookNodeId(){
		return fishBookNodeId;
	}
	
	/**
	* fishBookNodeId  String(2048)  //关卡特色按钮是否已点击查看记录    
	*/
	public void setFishBookNodeId(String fishBookNodeId){
		this.fishBookNodeId = fishBookNodeId;
	}
	
	/**
	* firstRecharge  Integer(10)  0  //0  未充值  1  已充值未领取  2  已领取    
	*/
	public Integer getFirstRecharge(){
		return firstRecharge;
	}
	
	/**
	* firstRecharge  Integer(10)  0  //0  未充值  1  已充值未领取  2  已领取    
	*/
	public void setFirstRecharge(Integer firstRecharge){
		this.firstRecharge = firstRecharge;
	}
	
	/**
	* firstRechargeData  String(128)  //首充数据,格式："7,1"，7表示充值表项目id，1表示充值时vip等级    
	*/
	public String getFirstRechargeData(){
		return firstRechargeData;
	}
	
	/**
	* firstRechargeData  String(128)  //首充数据,格式："7,1"，7表示充值表项目id，1表示充值时vip等级    
	*/
	public void setFirstRechargeData(String firstRechargeData){
		this.firstRechargeData = firstRechargeData;
	}
	
	/**
	* firstRechargeTime  Date(19)  //首充时间    
	*/
	public Date getFirstRechargeTime(){
		return firstRechargeTime;
	}
	
	/**
	* firstRechargeTime  Date(19)  //首充时间    
	*/
	public void setFirstRechargeTime(Date firstRechargeTime){
		this.firstRechargeTime = firstRechargeTime;
	}
	
	/**
	* secondRechargeTime  Date(19)  //第二笔充值时间    
	*/
	public Date getSecondRechargeTime(){
		return secondRechargeTime;
	}
	
	/**
	* secondRechargeTime  Date(19)  //第二笔充值时间    
	*/
	public void setSecondRechargeTime(Date secondRechargeTime){
		this.secondRechargeTime = secondRechargeTime;
	}
	
	/**
	* thirdRechargeTime  Date(19)  //第三笔充值时间    
	*/
	public Date getThirdRechargeTime(){
		return thirdRechargeTime;
	}
	
	/**
	* thirdRechargeTime  Date(19)  //第三笔充值时间    
	*/
	public void setThirdRechargeTime(Date thirdRechargeTime){
		this.thirdRechargeTime = thirdRechargeTime;
	}
	
	/**
	* luckyGiftsTime  Date(19)  //幸运礼包触发时间    
	*/
	public Date getLuckyGiftsTime(){
		return luckyGiftsTime;
	}
	
	/**
	* luckyGiftsTime  Date(19)  //幸运礼包触发时间    
	*/
	public void setLuckyGiftsTime(Date luckyGiftsTime){
		this.luckyGiftsTime = luckyGiftsTime;
	}
	
	/**
	* luckyGiftsIsGet  Integer(10)  0  //幸运礼包领取  0  未领取  1  已领取    
	*/
	public Integer getLuckyGiftsIsGet(){
		return luckyGiftsIsGet;
	}
	
	/**
	* luckyGiftsIsGet  Integer(10)  0  //幸运礼包领取  0  未领取  1  已领取    
	*/
	public void setLuckyGiftsIsGet(Integer luckyGiftsIsGet){
		this.luckyGiftsIsGet = luckyGiftsIsGet;
	}
	
	/**
	* luckyGiftsRe  Integer(10)  0  //幸运礼包充值金额  单位  0.01元    
	*/
	public Integer getLuckyGiftsRe(){
		return luckyGiftsRe;
	}
	
	/**
	* luckyGiftsRe  Integer(10)  0  //幸运礼包充值金额  单位  0.01元    
	*/
	public void setLuckyGiftsRe(Integer luckyGiftsRe){
		this.luckyGiftsRe = luckyGiftsRe;
	}
	
	/**
	* dayGainMoney  Long(19)  0  //每天收获的金币数    
	*/
	public Long getDayGainMoney(){
		return dayGainMoney;
	}
	
	/**
	* dayGainMoney  Long(19)  0  //每天收获的金币数    
	*/
	public void setDayGainMoney(Long dayGainMoney){
		this.dayGainMoney = dayGainMoney;
	}
	
	/**
	* isYesterdayLogin  Integer(10)  0  //玩家昨天是否登录过  0  没有  1  有    
	*/
	public Integer getIsYesterdayLogin(){
		return isYesterdayLogin;
	}
	
	/**
	* isYesterdayLogin  Integer(10)  0  //玩家昨天是否登录过  0  没有  1  有    
	*/
	public void setIsYesterdayLogin(Integer isYesterdayLogin){
		this.isYesterdayLogin = isYesterdayLogin;
	}
	
	/**
	* gunPower  Integer(10)  100000  //炮倍    
	*/
	public Integer getGunPower(){
		return gunPower;
	}
	
	/**
	* gunPower  Integer(10)  100000  //炮倍    
	*/
	public void setGunPower(Integer gunPower){
		this.gunPower = gunPower;
	}
	
	/**
	* sevenDay  Integer(10)  0  //七天登录奖励领取  作位运算    
	*/
	public Integer getSevenDay(){
		return sevenDay;
	}
	
	/**
	* sevenDay  Integer(10)  0  //七天登录奖励领取  作位运算    
	*/
	public void setSevenDay(Integer sevenDay){
		this.sevenDay = sevenDay;
	}
	
	/**
	* addOnlineTime  Integer(10)  0  //累计在线时长  秒    
	*/
	public Integer getAddOnlineTime(){
		return addOnlineTime;
	}
	
	/**
	* addOnlineTime  Integer(10)  0  //累计在线时长  秒    
	*/
	public void setAddOnlineTime(Integer addOnlineTime){
		this.addOnlineTime = addOnlineTime;
	}
	
	/**
	* onlineRd  Integer(10)  0  //在线奖励领取奖励  作位运算    
	*/
	public Integer getOnlineRd(){
		return onlineRd;
	}
	
	/**
	* onlineRd  Integer(10)  0  //在线奖励领取奖励  作位运算    
	*/
	public void setOnlineRd(Integer onlineRd){
		this.onlineRd = onlineRd;
	}
	
	/**
	* isLogin  Integer(10)  1  //今天是否登录过，0:没有  1:已登录过    
	*/
	public Integer getIsLogin(){
		return isLogin;
	}
	
	/**
	* isLogin  Integer(10)  1  //今天是否登录过，0:没有  1:已登录过    
	*/
	public void setIsLogin(Integer isLogin){
		this.isLogin = isLogin;
	}
	
	/**
	* clubId  Long(19)  0  //俱乐部id    
	*/
	public Long getClubId(){
		return clubId;
	}
	
	/**
	* clubId  Long(19)  0  //俱乐部id    
	*/
	public void setClubId(Long clubId){
		this.clubId = clubId;
	}
	
	/**
	* clubName  String(128)  //俱乐部名字    
	*/
	public String getClubName(){
		return clubName;
	}
	
	/**
	* clubName  String(128)  //俱乐部名字    
	*/
	public void setClubName(String clubName){
		this.clubName = clubName;
	}
	
	/**
	* dayAct  Integer(10)  0  //今日活跃    
	*/
	public Integer getDayAct(){
		return dayAct;
	}
	
	/**
	* dayAct  Integer(10)  0  //今日活跃    
	*/
	public void setDayAct(Integer dayAct){
		this.dayAct = dayAct;
	}
	
	/**
	* weekAct  Integer(10)  0  //本周活跃    
	*/
	public Integer getWeekAct(){
		return weekAct;
	}
	
	/**
	* weekAct  Integer(10)  0  //本周活跃    
	*/
	public void setWeekAct(Integer weekAct){
		this.weekAct = weekAct;
	}
	
	/**
	* dayActBox  Integer(10)  0  //今日活跃箱子领取  作位运算    
	*/
	public Integer getDayActBox(){
		return dayActBox;
	}
	
	/**
	* dayActBox  Integer(10)  0  //今日活跃箱子领取  作位运算    
	*/
	public void setDayActBox(Integer dayActBox){
		this.dayActBox = dayActBox;
	}
	
	/**
	* weekActBox  Integer(10)  0  //本周活跃箱子领取  作位运算    
	*/
	public Integer getWeekActBox(){
		return weekActBox;
	}
	
	/**
	* weekActBox  Integer(10)  0  //本周活跃箱子领取  作位运算    
	*/
	public void setWeekActBox(Integer weekActBox){
		this.weekActBox = weekActBox;
	}
	
	/**
	* makeUp  Integer(10)  0  //每日金币补足    
	*/
	public Integer getMakeUp(){
		return makeUp;
	}
	
	/**
	* makeUp  Integer(10)  0  //每日金币补足    
	*/
	public void setMakeUp(Integer makeUp){
		this.makeUp = makeUp;
	}
	
	/**
	* catchMoneyTree  Integer(10)  0  //捕获摇钱树次数    
	*/
	public Integer getCatchMoneyTree(){
		return catchMoneyTree;
	}
	
	/**
	* catchMoneyTree  Integer(10)  0  //捕获摇钱树次数    
	*/
	public void setCatchMoneyTree(Integer catchMoneyTree){
		this.catchMoneyTree = catchMoneyTree;
	}
	
	/**
	* nodes  String(128)  //进入过的节点ID    
	*/
	public String getNodes(){
		return nodes;
	}
	
	/**
	* nodes  String(128)  //进入过的节点ID    
	*/
	public void setNodes(String nodes){
		this.nodes = nodes;
	}
	
	/**
	* maxCatch  Long(19)  0  //单次最大捕获    
	*/
	public Long getMaxCatch(){
		return maxCatch;
	}
	
	/**
	* maxCatch  Long(19)  0  //单次最大捕获    
	*/
	public void setMaxCatch(Long maxCatch){
		this.maxCatch = maxCatch;
	}
	
	/**
	* seaGodBill  Long(19)  0  //海王币    
	*/
	public Long getSeaGodBill(){
		return seaGodBill;
	}
	
	/**
	* seaGodBill  Long(19)  0  //海王币    
	*/
	public void setSeaGodBill(Long seaGodBill){
		this.seaGodBill = seaGodBill;
	}
	
	/**
	* isValidate  Integer(10)  0  //0.为未验证  1.为未成人  2.为成人(主页防沉迷按钮  0显示  1、2隐藏)    
	*/
	public Integer getIsValidate(){
		return isValidate;
	}
	
	/**
	* isValidate  Integer(10)  0  //0.为未验证  1.为未成人  2.为成人(主页防沉迷按钮  0显示  1、2隐藏)    
	*/
	public void setIsValidate(Integer isValidate){
		this.isValidate = isValidate;
	}
	
	/**
	* onlineTime  Integer(10)  0  //在线时长  毫秒    
	*/
	public Integer getOnlineTime(){
		return onlineTime;
	}
	
	/**
	* onlineTime  Integer(10)  0  //在线时长  毫秒    
	*/
	public void setOnlineTime(Integer onlineTime){
		this.onlineTime = onlineTime;
	}
	
	/**
	* income  Integer(10)  100  //收益    
	*/
	public Integer getIncome(){
		return income;
	}
	
	/**
	* income  Integer(10)  100  //收益    
	*/
	public void setIncome(Integer income){
		this.income = income;
	}
	
	/**
	* antiOutTime  Integer(10)  0  //防沉迷零收益离线时长    
	*/
	public Integer getAntiOutTime(){
		return antiOutTime;
	}
	
	/**
	* antiOutTime  Integer(10)  0  //防沉迷零收益离线时长    
	*/
	public void setAntiOutTime(Integer antiOutTime){
		this.antiOutTime = antiOutTime;
	}
	
	/**
	* recGradeValue  Long(19)  0  //充值优质度(100万倍)    
	*/
	public Long getRecGradeValue(){
		return recGradeValue;
	}
	
	/**
	* recGradeValue  Long(19)  0  //充值优质度(100万倍)    
	*/
	public void setRecGradeValue(Long recGradeValue){
		this.recGradeValue = recGradeValue;
	}
	
	/**
	* actGradeValue  Long(19)  0  //活跃优质度(100万倍)    
	*/
	public Long getActGradeValue(){
		return actGradeValue;
	}
	
	/**
	* actGradeValue  Long(19)  0  //活跃优质度(100万倍)    
	*/
	public void setActGradeValue(Long actGradeValue){
		this.actGradeValue = actGradeValue;
	}
	
	/**
	* pearlFieldId  Integer(10)  0  //珍珠夺宝当前倍场    
	*/
	public Integer getPearlFieldId(){
		return pearlFieldId;
	}
	
	/**
	* pearlFieldId  Integer(10)  0  //珍珠夺宝当前倍场    
	*/
	public void setPearlFieldId(Integer pearlFieldId){
		this.pearlFieldId = pearlFieldId;
	}
	
	/**
	* cardScore  Integer(10)  0  //卡牌积分    
	*/
	public Integer getCardScore(){
		return cardScore;
	}
	
	/**
	* cardScore  Integer(10)  0  //卡牌积分    
	*/
	public void setCardScore(Integer cardScore){
		this.cardScore = cardScore;
	}
	
	/**
	* cardContorlTime  Date(19)  //卡牌积分控制生效时间    
	*/
	public Date getCardContorlTime(){
		return cardContorlTime;
	}
	
	/**
	* cardContorlTime  Date(19)  //卡牌积分控制生效时间    
	*/
	public void setCardContorlTime(Date cardContorlTime){
		this.cardContorlTime = cardContorlTime;
	}
	
	/**
	* carnivalGunPower  Integer(10)  0  //狂欢Jackpot关卡当前使用的炮倍    
	*/
	public Integer getCarnivalGunPower(){
		return carnivalGunPower;
	}
	
	/**
	* carnivalGunPower  Integer(10)  0  //狂欢Jackpot关卡当前使用的炮倍    
	*/
	public void setCarnivalGunPower(Integer carnivalGunPower){
		this.carnivalGunPower = carnivalGunPower;
	}
	
	/**
	* prizeCardBulletNum  Integer(10)  0  //大奖赛卡牌鱼子弹累积数    
	*/
	public Integer getPrizeCardBulletNum(){
		return prizeCardBulletNum;
	}
	
	/**
	* prizeCardBulletNum  Integer(10)  0  //大奖赛卡牌鱼子弹累积数    
	*/
	public void setPrizeCardBulletNum(Integer prizeCardBulletNum){
		this.prizeCardBulletNum = prizeCardBulletNum;
	}
	
	/**
	* jackpotCardBulletNum  Integer(10)  0  //卡牌jackpot卡牌鱼子弹累积数    
	*/
	public Integer getJackpotCardBulletNum(){
		return jackpotCardBulletNum;
	}
	
	/**
	* jackpotCardBulletNum  Integer(10)  0  //卡牌jackpot卡牌鱼子弹累积数    
	*/
	public void setJackpotCardBulletNum(Integer jackpotCardBulletNum){
		this.jackpotCardBulletNum = jackpotCardBulletNum;
	}
	
	/**
	* recPackBackBulletNum  Integer(10)  0  //充值返奖子弹累计数    
	*/
	public Integer getRecPackBackBulletNum(){
		return recPackBackBulletNum;
	}
	
	/**
	* recPackBackBulletNum  Integer(10)  0  //充值返奖子弹累计数    
	*/
	public void setRecPackBackBulletNum(Integer recPackBackBulletNum){
		this.recPackBackBulletNum = recPackBackBulletNum;
	}
	
	/**
	* discountGiftTime  Date(19)  //特价礼包活动购买时间    
	*/
	public Date getDiscountGiftTime(){
		return discountGiftTime;
	}
	
	/**
	* discountGiftTime  Date(19)  //特价礼包活动购买时间    
	*/
	public void setDiscountGiftTime(Date discountGiftTime){
		this.discountGiftTime = discountGiftTime;
	}
	
	/**
	* discountGiftCount  Integer(10)  0  //特价礼包已发邮件次数    
	*/
	public Integer getDiscountGiftCount(){
		return discountGiftCount;
	}
	
	/**
	* discountGiftCount  Integer(10)  0  //特价礼包已发邮件次数    
	*/
	public void setDiscountGiftCount(Integer discountGiftCount){
		this.discountGiftCount = discountGiftCount;
	}
	
	/**
	* hasDoGuide  Integer(10)  0  //是否已处理新手引导  0为未处理，1为玩家已进行过引导，2为玩家选择是老玩家    
	*/
	public Integer getHasDoGuide(){
		return hasDoGuide;
	}
	
	/**
	* hasDoGuide  Integer(10)  0  //是否已处理新手引导  0为未处理，1为玩家已进行过引导，2为玩家选择是老玩家    
	*/
	public void setHasDoGuide(Integer hasDoGuide){
		this.hasDoGuide = hasDoGuide;
	}
	
	/**
	* ttgtBulletNum  Integer(10)  0  //万炮宝藏子弹数    
	*/
	public Integer getTtgtBulletNum(){
		return ttgtBulletNum;
	}
	
	/**
	* ttgtBulletNum  Integer(10)  0  //万炮宝藏子弹数    
	*/
	public void setTtgtBulletNum(Integer ttgtBulletNum){
		this.ttgtBulletNum = ttgtBulletNum;
	}
	
	/**
	* shellTag  Integer(10)  0  //五彩贝壳(特殊鱼玩法)，每个玩家，首次开启规避0倍，0机会没使用  1已使用    
	*/
	public Integer getShellTag(){
		return shellTag;
	}
	
	/**
	* shellTag  Integer(10)  0  //五彩贝壳(特殊鱼玩法)，每个玩家，首次开启规避0倍，0机会没使用  1已使用    
	*/
	public void setShellTag(Integer shellTag){
		this.shellTag = shellTag;
	}
	
}