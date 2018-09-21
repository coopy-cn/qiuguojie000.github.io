import api from '../../service/index.js'
import {dealurl} from '../../utils/index.js'
Page({
  data: {
    courseId:null,
    subjects:[],
    havetitle:[],
    toView:'to0',
    courseTableConfig: {},
    teacherInfo:{},
    courseInfo: {},
    teachPlace: {},
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    disabled: false,
    url:'http://192.168.1.125'
  },
  
  onLoad (option) {//初始化
    let params = option;
    console.log('上一个页面传递过来的参数：')
    console.log(params)
    api.courseDetail({
      data: params,
      success: (res) => {
        console.log(res.data.Data.courseInfo)
        
        this.setData({
          courseTableConfig:res.data.Data.courseTableConfig,
          teacherInfo: res.data.Data.teacherInfo,
          courseInfo: res.data.Data.courseInfo,
          teachPlace: res.data.Data.teachPlace,
          courseId: params.id,
        })
      },
      fail: (err) => {

      },
    })
  },
  toSubscribe(event) {
    console.log(event.currentTarget.dataset)
    wx.navigateTo({
      url: '../user/add?courseId=' + event.currentTarget.dataset.id + '&courseUse=' + event.currentTarget.dataset.use,
      success: (res) => { },
      fail: (err) => {
        console.log(err)
      }
    });
  },
  imgDetail: function (event){
    var src = event.currentTarget.dataset.src;
    var imgList = new Array();
    imgList.push(src)
    //图片预览
    wx.previewImage({
      current: src, // 当前显示图片的http链接
      urls: imgList // 需要预览的图片http链接列表
    })
  },
  order : function(e){
      this.setData({
        disabled: !this.data.disabled
      })
  },
  know:function(e){
    console.log(1);
    wx.navigateTo({
      url: '../order/order'
    })
  }
})
