import api from '../../service/index.js'
const app = getApp()
Page({
  data: {
    imgalist: ['http://192.168.1.125/upload/conimg/QR.jpg'],
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    isCheck1:true,
    isCheck2: false,
    isCheck3: false,
    url: 'http://192.168.1.125'
  },
  onShow: function () {
    if (app.globalData.userInfo) {
      this.setData({
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        lang: "zh_CN",
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            hasUserInfo: true
          })
        }
      })
    }
    var that = this;
    api.index({
      success:function(res){
        that.setData({
          courseList : res.data.Data.courseList,
          placeList : res.data.Data.placeList,
          teacherList : res.data.Data.teacherList
        })
      }
    })
  },
  getUserInfo: function (e) {
    console.log(e)
    if (e.detail.userInfo) {
      //用户按了允许授权按钮
      // 登录
      wx.login({
        success: res => {
          api.login({
            data: {
              encryptedData: e.detail.encryptedData,
              rawData: e.detail.rawData,
              iv: e.detail.iv,
              code: res.code
            },
            success: function (resq) {
              console.log(resq)
              if (resq.data.resultCode == 200) {
                app.globalData.sk = resq.data.Data.sk;
              }
            }
          })
        }
      })

      app.globalData.userInfo = e.detail.userInfo
      this.setData({
        hasUserInfo: true
      })
    } else {
      //用户按了拒绝按钮
      this.setData({
        hasUserInfo: true
      })
    }
  },
  clicks1:function(){
    this.setData({
      isCheck1: true,
      isCheck2: false,
      isCheck3: false
    })
  },
  clicks2: function () {
    this.setData({
      isCheck1: false,
      isCheck2: true,
      isCheck3: false
    })
  },
  clicks3: function () {
    this.setData({
      isCheck1: false,
      isCheck2: false,
      isCheck3: true
    })
  },
  toDetail(event){
    wx.navigateTo({
      url: '../detail/detail?id=' + event.currentTarget.dataset.id,
      success: (res) => { },
      fail: (err) => {
        console.log(err)
      }
    });
  },
  toHome:function(){
    wx.switchTab({
      url: '/pages/home/home'
    })
  },
  placeMap(event){
    wx.getLocation({
      type: 'gcj02', //返回可以用于wx.openLocation的经纬度
      success: function (res) {
        var latitude = event.currentTarget.dataset.latitude * 1;
        var longitude = event.currentTarget.dataset.longitude * 1;
        wx.openLocation({
          latitude: latitude,
          longitude: longitude,
          name: event.currentTarget.dataset.name
        })
      }
    })
  },
  toTeach(event) {
    wx.navigateTo({
      url: '../teach/teach',
      success: (res) => { },
      fail: (err) => {
        console.log(err)
      }
    });
  },
  toLook(event){
    wx.previewImage({
      current: 'http://192.168.1.125/upload/conimg/QR.jpg', // 当前显示图片的http链接
      urls: this.data.imgalist // 需要预览的图片http链接列表
    })
  },
  onPullDownRefresh() {//下拉到顶部刷新
    var that = this;
    api.index({
      success: function (res) {
        that.setData({
          courseList: res.data.Data.courseList,
          placeList: res.data.Data.placeList,
          teacherList: res.data.Data.teacherList
        })
      }
    })
    wx.stopPullDownRefresh();
  },
  onShareAppMessage: function () {

  }
})
