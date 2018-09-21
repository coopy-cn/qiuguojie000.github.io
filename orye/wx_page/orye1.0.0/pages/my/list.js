// pages/my/list.js
import api from '../../service/index.js'
var app = getApp();
var util = require('../../utils/util.js');
var page = 1;
var list = new Array();
var totalPage=0;
Page({
  data:{
    timestamp:0
  },
  onReachBottom:function(){
    if(!this.data.nomore){
      page++;
      this.loadMore();
    }
  },
  getList(){
    var that = this;

    api.myPageList({
      data: { sk: app.globalData.sk, curPage: page},
      success:function(res){
        if (res.data.resultCode == 200) {
          if (res.data.Data.list.dataList.length==0){
            if(page==1){
              that.setData({ 'isnull': true });
            }
            that.setData({ nomore: true });
          }else{
            list = res.data.Data.list.dataList;
            that.setData({ list: list });
            totalPage = res.data.Data.list.totalPage;
            if (totalPage<=1){
              that.setData({ nomore: true });
            }
          }
        }
      }
    })

  },  
  loadMore() {//下拉刷新列表
    if (page > totalPage) {
      this.setData({
        nomore: true,
      });
      return;
    }
    var param = {};
    param.curPage = page;
    param.sk = app.globalData.sk;
    api.myPageList({
      data: param,
      success: (res) => {
        let newDatas = [...this.data.list, ...res.data.Data.list.dataList];
        this.setData({
          list: newDatas
        })
      },
      fail: (err) => {

      },
    })
  },
  onPullDownRefresh: function(){
    page = 1;
    this.getList();
    wx.stopPullDownRefresh();
  },
  onShow:function(){
    page = 1;
    var timestamp = Date.parse(new Date());
    this.setData({
      timestamp:timestamp,
    })
    this.getList();
  }
})