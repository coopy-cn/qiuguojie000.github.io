
// pages/my/card.js
import api from '../../service/index.js'
const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    cardList:null,
    phone:null,
  },
  skip (e) {
    let cardList = e.currentTarget.dataset.cardlist;
    let phone = e.currentTarget.dataset.phone;
    let data = JSON.stringify(cardList);
    console.log(data);
    wx.navigateTo({
      // es6模板字符串
      url:`../buy/buy?data=${data}&phone=${phone}`,
      })
  },
  playhome (e){
    console.log(1);
    //tab页面跳转
    wx.switchTab({
      url: '/pages/home/home'
    })
   
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
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
    let that = this;
    api.cardList({
      data:{
        sk: app.globalData.sk,
      },
      success(res) {
        console.log(res.data.Data.phone)
        that.setData({
          cardList: res.data.Data.list.dataList,
          phone: res.data.Data.phone
        })
      }
    })

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