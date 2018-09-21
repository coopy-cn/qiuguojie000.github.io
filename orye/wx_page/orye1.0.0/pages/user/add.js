import api from '../../service/index.js'
var util = require('../../utils/util.js');  
var app = getApp();
var today = util.formatTime(new Date((new Date()).getTime() + (1000 * 60 * 60 * 24 * 1))).split(' ')[0];
console.log(api.useCard)
Page({
  data:{
    courseId:null,
    courseUse:null,
    homeInfo:{},
    sex: ['请选择性别','男','女'],
    type:1,
    gender:0,
    date:today,
    time:'请选择时间',
    Surpluss:['请选择',1,2,3,4,5,6],
    surplus:0,
    isAgree: false,
    vehicle:'',
    departure:'住址',
    destination:'目的地',
    select: false,
    cardList: null,
    num: 0,
    
  },
  setSex:function(e){
    this.setData({gender:e.detail.value})
  },
  setCard (e){
    this.setData({num:e.detail.value})
  },
  bindDateChange:function(e){
    this.setData({
        date: e.detail.value
    })
  },
  bindAgreeChange: function (e) {
      this.setData({
          isAgree: !!e.detail.value.length
      });
  },

  

  formSubmit:function(e){
    console.log(this.data.num);
    var data = e.detail.value;
    var that = this;
    console.log(data);

    if(data.chirldName == ''){
      util.isError('请输入姓名', that);
      return false;
    }
    if(data.sex == 0){
      util.isError('请选择性别', that);
      return false;
    }
    if (data.birthDate == '请选择出生日期') {
      util.isError('请选择出生日期', that);
      return false;
    }
    if (data.motName == '') {
      util.isError('请输入妈妈姓名', that);
      return false;
    }
    if(data.motPhone == ''){
      util.isError('请输入妈妈手机号', that);
      return false;
    }
    if (!(/^1[34578]\d{9}$/.test(data.motPhone))) {
      util.isError('妈妈手机号错误', that);
      return false;
    }
    if (data.fatName == '') {
      util.isError('请输入爸爸姓名', that);
      return false;
    }
    if (data.fatPhone == '') {
      util.isError('请输入爸爸手机号', that);
      return false;
    }
    if (!(/^1[34578]\d{9}$/.test(data.fatPhone))){
      util.isError('爸爸手机号错误', that);
      return false;
    }
    if (data.email == '') {
      util.isError('请输入电子邮箱', that);
      return false;
    }
    let str = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    if (!str.test(data.email)){
      util.isError('电子邮箱错误', that);
      return false;
    }
    if(that.data.departure == '住址'){
      util.isError('请选择住址', that);
      return false;
    } 
    // if(!data.isAgree[0]){
    //   util.isError('请阅读并同意条款',that);
    //   return false;
    // }
    data.conId=that.data.courseId;
    data.sk = app.globalData.sk;
    data.address = that.data.departure;
    if(that.data.num ==0){
      if (app.globalData.sk) {
        api.submitOrder({
          data: data,
          success: function (resq) {
            if (resq.data.resultCode == 200) {
              console.log(resq.data.Data)
              wx.requestPayment(
                {
                  'appId': resq.data.Data.appId,
                  'timeStamp': resq.data.Data.timeStamp,
                  'nonceStr': resq.data.Data.nonceStr,
                  'package': resq.data.Data.package,
                  'signType': 'MD5',
                  'paySign': resq.data.Data.paySign,
                  'success': function (res) {
                    wx.showToast({
                      title: '预约成功',
                      icon: 'success',
                      duration: 2000,
                      success: function () {
                        setTimeout(function () {
                          wx.switchTab({
                            url: '/pages/home/home'
                          })
                        }, 2000)
                      }
                    })
                  },
                  'fail': function (res) {

                  },
                  'complete': function (res) {

                  }
                })

            } else {
              util.isError(resq.data.message, that);
              return false;
            }
          }
        })
      } else {
        util.isError("请先返回首页微信登陆", that);
        return false;
      }
    } else if (that.data.cardList[that.data.num].restCount >= that.data.courseUse){
      data.memId = that.data.cardList[that.data.num].id;
      api.submitOrder({
        data: data,
        success:function(res){
          if(res.data.resultCode == 200){
            console.log(res);
            wx.showToast({
              title: '预约成功',
              icon: 'success',
              duration: 2000,
              success: function () {
                setTimeout(function () {
                  wx.navigateBack({
                    delta: 2
                  })
                }, 2000)
              }
            })
          }
        }
      })
    }else{
      util.isError("次数不足",that);
      return false;
    }
    util.clearError(that);
    that.setData({
      select: true,
    })
  },
  sexDeparture:function(){
    var that = this;
    wx.chooseLocation({
      success:function(res){
        that.setData({
          departure:res.address
        })
      }
    })
  },
  onLoad:function(options){
    let params = options;
    console.log('上一个页面传递过来的参数：')
    console.log(params)
    this.setData({
      courseId: params.courseId,
      courseUse:params.courseUse,
    })
    var that = this;
    var data = {};
    data.sk = app.globalData.sk;
    api.homeInfo({
      data: data,
      success: function (resq) {
        if (resq.data.resultCode == 200) {
          that.setData({
            homeInfo: resq.data.Data.courseOrder,
            gender: resq.data.Data.courseOrder.sex,
            date: resq.data.Data.courseOrder.strBirthDate,
            departure: resq.data.Data.courseOrder.address,
          })
        }
      }
    })
    api.useCard({
      data: data,
      success:function(res){
        if(res.data.resultCode == 200){
          console.log(res.data)
          that.setData({
            cardList: [{memberName:'微信支付',restCount:""}, ...res.data.Data.list]
          })
        }
      }
    })
  }
})