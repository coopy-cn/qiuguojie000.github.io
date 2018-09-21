import api from '../../service/index.js'
import {dealurl} from '../../utils/index.js'
import util from '../../utils/util.js'
var app = getApp();

Page({
  data: {
    courseTable: [],//课程表数据
    pickers: {},//地区数据
    pickersIndex: [],//地区数据初始下标
    pickers1: {},//课程数据
    pickersIndex1: [],//课程数据初始下标
    hasMore: true,//load是否加载
    selectArray: [],//时间数据
    selectIndex: 0,//时间初始下标
    date: '',//日期数据
    checks: [],
    selectedDate: '',//选中的几月几号
    selectedDate1: '',//选中的几月几号
    selectedWeek: '',//选中的星期几
    curYear: 2017,//当前年份
    curMonth: 0,//当前月份
    daysCountArr: [// 保存各个月份的长度，平年
      31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    ],
    weekArr: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
    dateList: [],
    str:'日历',
    url:'http://192.168.1.125'
  },
  init(){//初始化数据
    var timestamp = Date.parse(new Date());
    var checks=[];
    for (var i = 0; i < 5; ++i) {
      var newTimestamp = timestamp + i * 24 * 60 * 60 * 1000;
      var date = new Date(newTimestamp);
      const year = date.getFullYear();
      const month = date.getMonth() + 1;
      const day = date.getDate();
      var obj={};
      if(i==0){
        obj.name = '今天';
      }else{
        obj.name = [month, day].map(util.formatNumber).join('-');
      }
      obj.value=i;
      obj.checked=false;
      obj.year = year;
      checks.push(obj);
    }
    this.setData({
      date: '日期',
      selectIndex: 0,
      selectArray: ['全时间段', '09:30-11:30', '16:00-18:00'],
      checks: checks
    });

    api.placeList({//地区
      data: {},
      success: (res) => {
        this.setData({
          pickers: res.data.Data.list,
          pickersIndex:[0,0,0],
        });
        wx.stopPullDownRefresh();

      },
      fail: (err) => {

      },
    }),
    api.courseList({//课程
      data: {},
      success: (res) => {
        this.setData({
          pickers1: res.data.Data.list,
          pickersIndex1:[0,0],
        });
        wx.stopPullDownRefresh();

      },
      fail: (err) => {

      },
    }),
    api.courseTable({//课程表
      data: { sk : app.globalData.sk},
      success: (res) => {
        if(res.data.resultCode=200){
          this.setData({
            courseTable: res.data.Data.list,
          });
          if (res.data.Data.list.totalPage <= 1) {
            this.setData({ hasMore: false });
          }
          
        }
        wx.stopPullDownRefresh();
      },
      fail: (err) => {

      },
    })
    console.log(this.data.courseTable)
  },
  formatNumber(n) {
    n = n.toString()
    return n[1] ? n : '0' + n
  },
  bindMultiPickerChange: function (e) {//地区选择改变时-点击确定后
    console.log('地区选择改变时，携带值为', e.detail.value)
    this.setData({
      pickersIndex: e.detail.value
    })
    this.searchCourseTable();
  },
  bindMultiPickerColumnChange: function (e) {//地区选择-未点击确定
    console.log('地区选择修改的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      pickers: this.data.pickers,
      pickersIndex: this.data.pickersIndex
    };
    //data.pickersIndex[e.detail.column] = e.detail.value;
    switch (e.detail.column){
      case 0:
        var cityId = data.pickers[0][e.detail.value].id;
        this.searchRegInfo(cityId);
        break;
      case 1:
        var regId = data.pickers[1][e.detail.value].id;
        this.searchPlaceInfo(regId);
        break;
    }  
  },
  searchRegInfo(cityId) {//根据城市查询区
    var data={};
    if (cityId){
      data.cityId = cityId;
    }
    api.placeList({
      data: data,
      success: (res) => {
        this.setData({
          pickers: res.data.Data.list,
        })
      },
      fail: (err) => {

      },
    })
  },
  searchPlaceInfo(regId) {//根据区查询站点
    var data = {};
    if (regId) {
      data.regId = regId;
    }
    api.placeList({
      data: data,
      success: (res) => {
        this.setData({
          pickers: res.data.Data.list,
        })
      },
      fail: (err) => {

      },
    })
  },
  searchCourseTable() {//下拉选择查询列表
    var param = {};

    if (this.data.pickersIndex1[1]>0){
      param.courseId = this.data.pickers1[1][this.data.pickersIndex1[1]].id;
    }
    if (this.data.pickersIndex[2] > 0) {
      param.placeId = this.data.pickers[2][this.data.pickersIndex[2]].id;
    }
    if (this.data.selectIndex > 0) {
      param.time = this.data.selectIndex;
    }
    
    param.date = this.data.checks;
    param.sk = app.globalData.sk;
    api.courseTable({
      data: param,
      success: (res) => {
        if (res.data.resultCode = 200) {
          this.setData({
            courseTable: res.data.Data.list,
          })
          if (res.data.Data.list.totalPage <= 1) {
            this.setData({ hasMore: false });
          }else{
            this.setData({ hasMore: true });
          }
        }
      },
      fail: (err) => {

      },
    })
  },
  bindMultiPickerChange1: function (e) {//课程选择改变时-点击确定后
    console.log('课程选择改变，携带值为', e.detail.value)
    this.setData({
      pickersIndex1: e.detail.value
    })
    this.searchCourseTable();
  },
  bindMultiPickerColumnChange1: function (e) {//课程选择-未点击确定
    console.log('课程选择的列为', e.detail.column, '，值为', e.detail.value);
    var data = {
      pickers1: this.data.pickers1,
      pickersIndex1: this.data.pickersIndex1
    };
    data.pickersIndex1[e.detail.column] = e.detail.value;
    switch (e.detail.column) {
      case 0:
        var typeId = data.pickers1[0][e.detail.value].id;
        this.searchCourseInfo(typeId);
        break;
    }
  },
  searchCourseInfo(typeId) {//根据课类查询课程
    var data = {};
    if (typeId) {
      data.typeId = typeId;
    }
    api.courseList({
      data: data,
      success: (res) => {
        this.setData({
          pickers1: res.data.Data.list,
        })
      },
      fail: (err) => {

      },
    })
  },
  bindMultiPickerChange2: function (e) {//时间段改变时-点击确定后
    console.log('时间段改变，携带值为', e.detail.value)
    this.setData({
      selectIndex: e.detail.value
    })
    this.searchCourseTable();
  },
  bindDateChange: function (e) {//日期改变时-点击确定后
    console.log('日期改变，携带值为', e.detail.value)
    this.setData({
      date: e.detail.value
    })
    this.searchCourseTable();
  },
  onShow () {//页面启动
    var today = new Date();//当前时间  
    var y = today.getFullYear();//年  
    var mon = today.getMonth() + 1;//月  
    var d = today.getDate();//日  
    var i = today.getDay();//星期  

    var monstr = mon + '';
    if (mon < 10) {
      monstr = '0' + mon;
    }

    var dstr = d + '';
    if (d < 10) {
      dstr = '0' + d;
    }

    this.setData({
      curYear: y,
      curMonth: mon,
      selectedDate: y + '-' + monstr + '-' + dstr,
      selectedWeek: this.data.weekArr[i],
      selectedDate1: y + '-' + monstr + '-' + dstr,
    });

    this.getDateList(y, mon - 1);
    this.init()

  },
  loadMore(){//下拉刷新列表
    let currentPage = this.data.courseTable.curPage;
    if (currentPage >= this.data.courseTable.totalPage) {
        this.setData({
            hasMore: false,
        });
        return;
    }

    var param = {};

    if (this.data.pickersIndex1[1] > 0) {
      param.courseId = this.data.pickers1[1][this.data.pickersIndex1[1]].id;
    }
    if (this.data.pickersIndex[2] > 0) {
      param.placeId = this.data.pickers[2][this.data.pickersIndex[2]].id;
    }
    if (this.data.selectIndex > 0) {
      param.time = this.data.selectIndex;
    }
    if (this.data.date != '日期') {
      param.date = this.data.date;
    }
    param.curPage = currentPage+1;
    param.sk = app.globalData.sk;
    api.courseTable({
      data: param,
      success:(res)=>{
        let newDatas = res.data.Data.list;
        newDatas.dataList = [...this.data.courseTable.dataList,...newDatas.dataList];
        this.setData({
          courseTable:newDatas,
        })
      },
      fail:(err)=>{

      },
    })
  },
  clicks: function (e) {
    let index = e.currentTarget.dataset.index;
    if(index!=4){
      let arrs = this.data.checks;
      if (arrs[index].checked == false) {
        arrs[index].checked = true;
      } else {
        arrs[index].checked = false;
      }
      this.setData({
        checks: arrs
      })
      this.searchCourseTable();
    }else{
      let arrs = this.data.checks;
      if (arrs[4].checked == true) {
        arrs[4].checked = false;
        this.setData({
          checks: arrs
        })
        this.searchCourseTable();
      }
    }
  },
  toCourse(event) {
    wx.navigateTo({
      url: '../course/course?id=' + event.currentTarget.dataset.id,
      success: (res) => { },
      fail: (err) => {
        console.log(err)
      }
    });
  },
  toDetail(event) {
    wx.navigateTo({
      url: '../detail/detail?id=' + event.currentTarget.dataset.id,
      success: (res) => { },
      fail: (err) => {
        console.log(err)
      }
    });
  },
  onPullDownRefresh () {//下拉到顶部刷新
    this.init()
  },
  onReachBottom() {//上拉到底部刷新
    this.loadMore()
  },
  getDateList: function (y, mon) {
    var vm = this;
    //如果是否闰年，则2月是29日
    var daysCountArr = this.data.daysCountArr;
    if (y % 4 == 0 && y % 100 != 0) {
      this.data.daysCountArr[1] = 29;
      this.setData({
        daysCountArr: daysCountArr
      });
    }
    //第几个月；下标从0开始实际月份还要再+1  
    var dateList = [];
    // console.log('本月', vm.data.daysCountArr[mon], '天');
    dateList[0] = [];
    var weekIndex = 0;//第几个星期
    for (var i = 0; i < vm.data.daysCountArr[mon]; i++) {
      var week = new Date(y, mon, (i + 1)).getDay();
      // 如果是新的一周，则新增一周
      if (week == 0) {
        weekIndex++;
        dateList[weekIndex] = [];
      }
      // 如果是第一行，则将该行日期倒序，以便配合样式居右显示
      var monstr = mon + 1 + '';
      if (mon < 9) {
        monstr = '0' + (mon + 1);
      }

      var dstr = i + 1 + '';
      if (i < 9) {
        dstr = '0' + (i + 1);
      }
      if (weekIndex == 0) {
        dateList[weekIndex].unshift({
          value: y + '-' + monstr + '-' + dstr,
          date: i + 1,
          week: week
        });
      } else {
        dateList[weekIndex].push({
          value: y + '-' + monstr + '-' + dstr,
          date: i + 1,
          week: week
        });
      }
    }
    // console.log('本月日期', dateList);
    vm.setData({
      dateList: dateList
    });
  },
  selectDate: function (e) {
    var vm = this;
    // console.log('选中', e.currentTarget.dataset.date.value);

    var nows = vm.data.selectedDate1.replace(/-/g, '');

    var nexts = e.currentTarget.dataset.date.value.replace(/-/g, '');

    if(nows <= nexts){
      vm.setData({
        selectedDate: e.currentTarget.dataset.date.value,
        selectedWeek: vm.data.weekArr[e.currentTarget.dataset.date.week]
      });
    }
  },
  preMonth: function () {
    // 上个月
    var vm = this;
    var curYear = vm.data.curYear;
    var curMonth = vm.data.curMonth;
    curYear = curMonth - 1 ? curYear : curYear - 1;
    curMonth = curMonth - 1 ? curMonth - 1 : 12;
    // console.log('上个月', curYear, curMonth);
    vm.setData({
      curYear: curYear,
      curMonth: curMonth
    });

    vm.getDateList(curYear, curMonth - 1);
  },
  nextMonth: function () {
    // 下个月
    var vm = this;
    var curYear = vm.data.curYear;
    var curMonth = vm.data.curMonth;
    curYear = curMonth + 1 == 13 ? curYear + 1 : curYear;
    curMonth = curMonth + 1 == 13 ? 1 : curMonth + 1;
    // console.log('下个月', curYear, curMonth);
    vm.setData({
      curYear: curYear,
      curMonth: curMonth
    });

    vm.getDateList(curYear, curMonth - 1);
  },
  setModalStatus: function (e) {
    console.log("设置显示状态，1显示0不显示", e.currentTarget.dataset.status);
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export()
    })
    if (e.currentTarget.dataset.status == 1) {
      this.setData(
        {
          showModalStatus: true
        }
      );
    }
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation
      })
      if (e.currentTarget.dataset.status == 0) {
        this.setData(
          {
            showModalStatus: false
          }
        );
      }
    }.bind(this), 200)
  },
  subDate:function(){
    var s = this.data.selectedDate;
    s = s.substring(5);
    this.setData({
      str: s
    })
    let arrs = this.data.checks;
    if (arrs[4].checked == false) {
      arrs[4].checked = true;
      arrs[4].name=s;
    }
    this.setData({
      checks: arrs,
      showModalStatus: false
    })
    this.searchCourseTable();

  }
})
