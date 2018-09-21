// pages/buy/buy.js
import api from "../../service/index.js";
var util = require('../../utils/util.js');  
let app = getApp();
console.log(app)
Page({

  /**
   * 页面的初始数据
   */
  data: {
    buyList: null,
    phone: null,
  },

  playCard (){
    if (app.globalData.sk) {
      
      api.purchase({
        data: {
          sk: app.globalData.sk,
          memId: this.data.buyList.id
        },
        success: function (resq) {
          console.log(1);
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
                    title: '购买成功',
                    icon: 'success',
                    duration: 2000,
                    success: function () {
                      setTimeout(function () {
                        wx.navigateTo({
                          url: '/pages/my/card',
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
            util.isError(resq.data.message, this);
            return false;
          }
        }
      })
    } else {
      util.isError("请先返回首页微信登陆", this);
      return false;
    }
    util.clearError(this);
  },

 
  
  onLoad: function (options) {
    let dataList = JSON.parse(options.data);
    let that = this;
    that.setData({
      buyList: dataList,
      phone: options.phone,
    })
    console.log(that.data.buyList)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
   
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})