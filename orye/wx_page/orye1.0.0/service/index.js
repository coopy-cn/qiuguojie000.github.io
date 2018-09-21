
const app = getApp();
const host = ''
const wxRequest = (params, url) => {
  // wx.showToast({
  //   title: '加载中',
  //   icon: 'loading'
  // })
  wx.request({
    url: url,
    method: params.method || 'GET',
    data: params.data || {},
    header: {
      'Content-Type': 'application/json'
    },
    success: (res) => {
      params.success && params.success(res)
    },
    fail: (res) => {
      params.fail && params.fail(res)
    },
    complete: (res) => {
      params.complete && params.complete(res)
    }
  })
}

//const ip ='http://192.168.1.125:7005';
 const ip = 'http://192.168.1.77:8083';
//传地址
const purl = (params) => wxRequest(params, params.url)

const placeList = (params) => wxRequest(params, ip+'/apiAll/placeList.do')

const courseList = (params) => wxRequest(params, ip +'/apiAll/courseList.do')

const courseTable = (params) => wxRequest(params, ip +'/apiAll/courseTable.do')

const courseDetail = (params) => wxRequest(params, ip +'/apiAll/courseDetail.do')

const submitOrder = (params) => wxRequest(params, ip +'/apiAll/submitOrder.do')

const login = (params) => wxRequest(params, ip +'/apiAll/login.do')

const homeInfo = (params) => wxRequest(params, ip +'/apiAll/homeInfo.do')

const myPageList = (params) => wxRequest(params, ip +'/apiAll/myPageList.do')

const noTogoCount = (params) => wxRequest(params, ip +'/apiAll/noTogoCount.do')

const index = (params) => wxRequest(params, ip +'/apiAll/index.do')

const teacher = (params) => wxRequest(params, ip +'/apiAll/teacher.do')

const cardList = (params) => wxRequest(params, ip+"/api/memberList.do")
const purchase = (params) => wxRequest(params, ip +"/api/buyMember.do")
const useCard = (params) => wxRequest(params, ip +"/api/myMember.do")
module.exports = {
  purl,
  placeList,
  courseList,
  courseTable,
  courseDetail,
  submitOrder,
  login,
  homeInfo,
  myPageList,
  noTogoCount,
  index,
  teacher,
  cardList,
  purchase,
  useCard,
}
