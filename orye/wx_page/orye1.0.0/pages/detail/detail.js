// pages/detail/detail.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tab:[{
      one: "课程次数（每次课时约100-120分钟）/ 价格（元）",
      two: "体验课",
      three: "1次",
      four: "12次",
      five: "36次",
      six:"72次"
    },{
      one:"每次价格",
      two: "99",
      three: "249",
      four: "",
      five: "",
      six:""
    },{
      one: "总价格",
      two: "———",
      three: "",
      four: "2772",
      five: "7920",
      six:"14116"
    },{
      one: "3人团（9.5折）",
      two: "",
      three: "",
      four: "2663",
      five: "7524",
      six:"13410"
    },{
      one: "6人团（8.9折）",
      two: "66",
      three: "",
      four: "2467",
      five: "7049",
      six:"12563"
    },{
      one: "10人团（7.9折）",
      two: "",
      three: "",
      four: "2190",
      five: "6257",
      six:"9881"
    }
    
    ],
    url: 'http://192.168.1.125'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let params = options;
    console.log('上一个页面传递过来的参数：')
    console.log(params);
    this.setData({
      toImg: params.id,
    })
    
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