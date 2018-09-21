//app.js
import api from '/service/index.js'
App({
  onLaunch: function () {
    var that = this;
    // 登录
    wx.login({
      
      success: res => {
        // 获取用户信息
        wx.getSetting({
          success: res1 => {
            if (res1.authSetting['scope.userInfo']) {
              // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
              wx.getUserInfo({
                lang: "zh_CN",
                success: res1 => {
                  api.login({
                    data: {
                      encryptedData: res1.encryptedData,
                      rawData: res1.rawData,
                      iv: res1.iv,
                      code: res.code
                    },
                    success: function (resq) {
                      if (resq.data.resultCode == 200) {
                        that.globalData.sk = resq.data.Data.sk;
                        that.globalData.url = resq.data.Data.url;
                      }
                    }
                  })

                  // 可以将 res 发送给后台解码出 unionId
                  that.globalData.userInfo = res1.userInfo

                  // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                  // 所以此处加入 callback 以防止这种情况
                  if (that.userInfoReadyCallback) {
                    that.userInfoReadyCallback(res1)
                  }
                }
              })
            }
          }
        })
      }
    })
    
  },
  globalData: {
    userInfo: null,
    sk:null,
    url:''
  }

  // onLaunch: function () {
  //   var that = this;
  //   wx.checkSession({
  //     success: function () {
  //       wx.getStorage({
  //         key: 'sk',
  //         success: function (res) {
  //           var sk = res.data;
  //           wx.request({
  //             url: 'http://localhost:8080/testPay/loginValid.do',
  //             data: {
  //               sk: sk
  //             },
  //             success: function (resq) {
  //               if (resq.data.resultCode == 200) {
  //                 that.globalData.sk = sk;
  //               } else {
  //                 that.login();
  //                 return;
  //               }
  //             }
  //           })
  //         },
  //         fail: function () {
  //           that.login();
  //           return;
  //         }
  //       })

  //       wx.getStorage({
  //         key: 'userInfo',
  //         success: function (res) {
  //           that.globalData.userInfo = res.data;
  //         },
  //         fail: function () {
  //           that.login();
  //         }
  //       });
  //     },
  //     fail: function () {
  //       //登录态过期
  //       that.login() //重新登录
  //     }
  //   })
  // },
  // login: function () {
  //   var that = this;
  //   wx.login({
  //     success: function (res) {
  //       wx.getUserInfo({
  //         success: function (userinfo) {
  //           wx.request({
  //             url: 'http://localhost:8080/testPay/login.do',
  //             data: {
  //               code: res.code,
  //               encryptedData: userinfo.encryptedData,
  //               iv: userinfo.iv
  //             },
  //             success: function (resq) {
  //               console.log(resq)
  //               that.setUserInfo(resq.data.Data.userInfo);
  //               that.setSk(resq.data.Data.sk);
  //             }
  //           })
  //         },
  //         fail: function (res) {
  //           console.log(res);
  //           that.loginFail();
  //         }
  //       })
  //     }
  //   })
  // },
  // loginFail: function () {
  //   var that = this;
  //   wx.showModal({
  //     content: '登录失败，请允许获取用户信息,如不显示请删除小程序重新进入',
  //     showCancel: false
  //   });
    
  // },
  // setUserInfo: function (data) {   //将用户信息缓存保存
  //   this.globalData.userInfo = data;
  //   wx.setStorage({
  //     key: "userInfo",
  //     data: data
  //   })
  // },
  // setSk: function (data) {   //将用户信息缓存保存
  //   this.globalData.sk = data;
  //   wx.setStorage({
  //     key: "sk",
  //     data: data
  //   })
  // },
  // globalData: {
  //   userInfo: null,
  //   sk: null
  // }
})
