//index.js
//获取应用实例
import api from '../../service/index.js'
var app = getApp();

Page({
  data: {
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
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
    that.setData({
      userInfo: app.globalData.userInfo
    });

    api.noTogoCount({
      data: { sk: app.globalData.sk},
      success:function(res){
        if(res.data.resultCode=200){
          that.setData({
            count: res.data.Data.count
          });
        }
      }
    })
  },
  getUserInfo: function (e) {
    console.log(e)
    if (e.detail.userInfo) {
      var that = this;
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
                api.noTogoCount({
                  data: { sk: resq.data.Data.sk },
                  success: function (res) {
                    if (res.data.resultCode = 200) {
                      that.setData({
                        count: res.data.Data.count
                      });
                    }
                  }
                })
              }
            }
          })
        }
      })

      app.globalData.userInfo = e.detail.userInfo
      this.setData({
        hasUserInfo: true,
        userInfo: e.detail.userInfo
      })
    } else {

    }
  }
})
