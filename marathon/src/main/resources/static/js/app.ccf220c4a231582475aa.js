webpackJsonp([1],{288:function(t,e,a){"use strict";function s(){p=v.Loading.service({fullscreen:!0,background:"rgba(0, 0, 0, .2)"})}var i=a(175),r=a.n(i),n=a(352),l=a.n(n),o=a(16),u=a(319),c=a.n(u),d=a(689),h=a.n(d),v=a(168),p=(a.n(v),void 0),m=c.a.CancelToken,f=m.source();c.a.defaults.baseURL="http://localhost:8999/",c.a.interceptors.request.use(function(t){return t},function(t){return l.a.reject(t)}),c.a.interceptors.response.use(function(t){return p&&p.close(),console.log("---请求成功"),console.log(t),t},function(t){return document.write("页面出现了异常,请将下面内容截图联系工作人员\n "+t)});var g={get:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};t.loading&&s();var e=t.url;return t.data&&(e+="?"+h.a.stringify(t.data)),c.a.get(e).then(t.success).catch(function(t){})},post:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};t.loading&&s();var e=r()({},t.config,{cancelToken:f.token});return c.a.post(t.url,h.a.stringify(t.data),e).then(t.success).catch(function(t){})},formData_post:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return t.loading&&s(),c.a.post(t.url,t.data,{headers:{"Content-Type":"multipart/form-data"}}).then(t.success).catch(function(t){})},all:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[],e=arguments[1];return c.a.all(t).then(c.a.spread(e)).catch(function(t){})},stop:function(){f.cancel()}};o.default.prototype.$axios=g},289:function(t,e,a){"use strict";var s=a(16),i={addClass:function(t,e){if(!this.hasClass(t,e)){var a=t.className.split(" ");a.push(e),t.className=a.join(" ")}},hasClass:function(t,e){return new RegExp("(^|\\s)"+e+"(\\s|$)").test(t.className)},removeClass:function(t,e){if(this.hasClass(t,e)){var a=t.className.split(" "),s=a.indexOf(e);a.splice(s,1),t.className=a.join(" ")}},formatDate:function(t){var e=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"-",a=arguments.length>2&&void 0!==arguments[2]?arguments[2]:":",s=t.getFullYear(),i=t.getMonth()+1,r=t.getDate(),n=t.getHours(),l=t.getMinutes();return i=i>=10?i:"0"+i,n=n<10?"0"+n:n,l=l<10?"0"+l:l,""+s+e+i+e+r+" "+n+a+l},emptyObject:function(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};for(var e in t)return!1;return!0},checkPhone:function(t){return/^[1][3,4,5,7,8][0-9]{9}$/.test(t)}};s.default.prototype.$utils=i},290:function(t,e,a){"use strict";var s=a(16),i=a(706),r=a(695),n=a.n(r),l=a(696),o=a.n(l),u=a(697),c=a.n(u),d=a(698),h=a.n(d),v=a(699),p=a.n(v);s.default.use(i.a),e.a=new i.a({routes:[{path:"/",component:n.a},{path:"/b",component:o.a},{path:"/c",component:c.a},{path:"/d",component:h.a},{path:"/e",component:p.a}]})},291:function(t,e,a){"use strict";var s=a(16),i=a(709),r=a(345),n=(a.n(r),a(346)),l=a(349),o=a(348),u=a(708);a.n(u);s.default.use(i.a);e.a=new i.a.Store({strict:!1,plugins:[],state:l.a,getters:n,mutations:o.a,actions:r})},293:function(t,e){},294:function(t,e){},295:function(t,e){},296:function(t,e,a){function s(t){a(682)}var i=a(64)(a(338),a(705),s,null,null);t.exports=i.exports},338:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:function(){return{curPath:"/"}},created:function(){var t=this.$route.path;this.curPath!=t&&(this.curPath=t)}}},339:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:function(){return{value:"",list:["192.168.1.1","192.168.1.2","192.168.1.3"],myChart:"",xData:[],data:[],interval:"",gap:60,intervalFlag:!0}},created:function(){},mounted:function(){var t=this;this.myChart=this.$echarts.init(this.$refs.myChart),window.onresize=function(){t.myChart.resize({width:"auto"})},this.searchButton()},methods:{getData:function(){var t=this;setTimeout(function(){var e=t.$utils.formatDate(new Date),a=Math.ceil(9*Math.random())+1;t.xData.push(e),t.data.push(a),t.draw()},200)},searchButton:function(){var t=this;if(this.myChart){this.myChart.showLoading(),clearInterval(this.interval),this.xData=[],this.data=[];var e="";e=this.value?this.value:"实时访问情况",this.myChart.setOption({title:{text:e}}),this.getData();var a=[],s=[],i=new Date,r=setInterval(function(){if(t.intervalFlag){t.intervalFlag=!1,setTimeout(function(){t.intervalFlag=!0},200);var e=Math.ceil(9*Math.random())+1;i.setMinutes(i.getMinutes()-1);var n=t.$utils.formatDate(i);a.push(n),s.push(e)}if(10==a.length){var l,o;a.reverse(),s.reverse(),(l=t.xData).unshift.apply(l,a),(o=t.data).unshift.apply(o,s),t.draw(),console.log(t.xData),console.log(t.data),clearInterval(r)}},200);this.interval=setInterval(function(){t.getData()},1e3*Number(this.gap))}},draw:function(){this.myChart.hideLoading();var t={color:["pink"],tooltip:{trigger:"axis"},legend:{data:["数据"]},grid:{left:"5%",right:"5%"},xAxis:{type:"category",axisTick:{alignWithLabel:!0},data:this.xData},yAxis:{type:"value",name:"次数",nameGap:"10",axisLabel:{formatter:"{value} 次"}},series:[{name:"数据",type:"line",data:this.data,markPoint:{data:[{type:"max",name:"最大值"}]}}]};this.myChart.setOption(t)}}}},340:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:function(){return{value:"",list:["192.168.1.1","192.168.1.2","192.168.1.3"],dateValue:[],myChart:"",xData:[],reqList:[],errorList:[]}},created:function(){},mounted:function(){var t=this;this.myChart=this.$echarts.init(this.$refs.myChart),window.onresize=function(){t.myChart.resize({width:"auto"})},this.draw()},methods:{dateChange:function(){this.dateValue&&2==this.dateValue.length&&this.$axios.post({url:"/ipMonitor/firstDestIpMonitor",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1])},success:function(t){console.log("----"),console.log(t)}})},getData:function(){var t=this;return this.dateValue&&2==this.dateValue.length?this.value?void(this.myChart&&(this.myChart.showLoading(),this.xData=[],this.reqList=[],this.errorList=[],this.myChart.clear(),this.$axios.post({url:"/ipMonitor/destIpMonitor",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1]),destIp:this.value},success:function(e){console.log("----"),console.log(e),t.draw()}}))):void this.$message({message:"请选择目标IP",type:"error",duration:1500}):void this.$message({message:"请选择时间范围",type:"error",duration:1500})},draw:function(){this.myChart.hideLoading();var t={color:["#5fb4e3","#f75752"],tooltip:{trigger:"axis"},legend:{data:["请求数","错误数"]},grid:{left:"5%",right:"5%"},xAxis:{type:"category",axisTick:{alignWithLabel:!0},data:this.xData},yAxis:{type:"value",name:"次数",nameGap:"10",axisLabel:{formatter:"{value} 次"}},series:[{name:"请求数",type:"line",data:this.reqList},{name:"错误数",type:"line",data:this.errorList}]};this.value&&(t.title={text:"目标IP: "+this.value}),this.myChart.setOption(t)}}}},341:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:function(){return{value:"",list:["192.168.1.1","192.168.1.2","192.168.1.3"],dateValue:[],myChart:"",xData:[],reqList:[],errorList:[]}},created:function(){},mounted:function(){var t=this;this.myChart=this.$echarts.init(this.$refs.myChart),window.onresize=function(){t.myChart.resize({width:"auto"})},this.draw()},methods:{dateChange:function(){this.dateValue&&2==this.dateValue.length&&this.$axios.post({url:"/ipMonitor/firstSourceIpMonitor",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1])},success:function(t){console.log("----"),console.log(t)}})},getData:function(){var t=this;return this.dateValue&&2==this.dateValue.length?this.value?void(this.myChart&&(this.myChart.showLoading(),this.xData=[],this.reqList=[],this.errorList=[],this.myChart.clear(),this.$axios.post({url:"/ipMonitor/sourceIpMonitor",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1]),sourceIp:this.value},success:function(e){console.log("----"),console.log(e),t.draw()}}))):void this.$message({message:"请选择来源IP",type:"error",duration:1500}):void this.$message({message:"请选择时间范围",type:"error",duration:1500})},draw:function(){this.myChart.hideLoading();var t={color:["#5fb4e3","#f75752"],tooltip:{trigger:"axis"},legend:{data:["请求数","错误数"]},grid:{left:"5%",right:"5%"},xAxis:{type:"category",axisTick:{alignWithLabel:!0},data:this.xData},yAxis:{type:"value",name:"次数",nameGap:"10",axisLabel:{formatter:"{value} 次"}},series:[{name:"请求数",type:"line",data:this.reqList},{name:"错误数",type:"line",data:this.errorList}]};this.value&&(t.title={text:"来源IP: "+this.value}),this.myChart.setOption(t)}}}},342:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a(356),i=a.n(s);e.default={data:function(){return{value:"",list:["/a","/b","/c"],dateValue:[],myChart:"",xData:[],reqList:[],errorList:[]}},created:function(){},mounted:function(){var t=this;this.myChart=this.$echarts.init(this.$refs.myChart),window.onresize=function(){t.myChart.resize({width:"auto"})},this.draw()},methods:{dateChange:function(){var t=this;this.list=[],this.value="",this.dateValue&&2==this.dateValue.length&&(this.myChart.showLoading(),this.xData=[],this.reqList=[],this.errorList=[],this.myChart.clear(),this.$axios.post({url:"/urlMonitor/firstUrlMonitor",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1])},loading:!0,success:function(e){var a=e.data;if(console.log("----"),console.log(a),a.success){if(!a.result.url)return t.$message({message:"未查到相关数据",type:"warning",duration:1500}),void t.myChart.hideLoading();t.list=[].concat(i()(a.result.urls)),t.value=a.result.url,t.xData=[].concat(i()(a.result.xAxis)),t.reqList=[].concat(i()(a.result.urlRequestCount)),t.errorList=[].concat(i()(a.result.urlFailCount)),t.draw()}}}))},getData:function(){var t=this;return this.dateValue&&2==this.dateValue.length?this.value?void(this.myChart&&(this.myChart.showLoading(),this.xData=[],this.reqList=[],this.errorList=[],this.myChart.clear(),this.$axios.post({url:"/urlMonitor/urlMonitor",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1]),url:this.value},success:function(e){console.log("----"),console.log(e),t.draw()}}))):void this.$message({message:"请选择URL",type:"error",duration:1500}):void this.$message({message:"请选择时间范围",type:"error",duration:1500})},draw:function(){this.myChart.hideLoading();var t={color:["#5fb4e3","#f75752"],tooltip:{trigger:"axis"},legend:{data:["请求数","错误数"]},grid:{left:"5%",right:"5%"},xAxis:{type:"category",axisTick:{alignWithLabel:!0},data:this.xData},yAxis:{type:"value",name:"次数",nameGap:"10",axisLabel:{formatter:"{value} 次"}},series:[{name:"请求数",type:"line",data:this.reqList},{name:"错误数",type:"line",data:this.errorList}]};this.value&&(t.title={text:"URL: "+this.value}),this.myChart.setOption(t)}}}},343:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),e.default={data:function(){return{value:"",list:["/a","/b","/c"],dateValue:[],myChart:"",xData:[],max:[],min:[],average:[],percent:[]}},computed:{},created:function(){},mounted:function(){var t=this;this.myChart=this.$echarts.init(this.$refs.myChart),window.onresize=function(){t.myChart.resize({width:"auto"})},this.draw()},methods:{dateChange:function(){this.dateValue&&2==this.dateValue.length?this.$axios.post({url:"/urlMonitor/firstUrlMonitorSummary",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1])},success:function(t){console.log("----"),console.log(t)}}):this.list=[]},getData:function(){var t=this;return this.dateValue&&2==this.dateValue.length?this.value?void(this.myChart&&(this.myChart.showLoading(),this.xData=[],this.max=[],this.min=[],this.average=[],this.percent=[],this.myChart.clear(),this.$axios.post({url:"/urlMonitor/urlMonitorSummary",data:{beginTime:this.$utils.formatDate(this.dateValue[0]),endTime:this.$utils.formatDate(this.dateValue[1]),url:this.value},success:function(e){console.log("----"),console.log(e),t.draw()}}))):void this.$message({message:"请选择URL",type:"error",duration:1500}):void this.$message({message:"请选择时间范围",type:"error",duration:1500})},draw:function(){this.myChart.hideLoading();var t={tooltip:{trigger:"axis"},legend:{data:["最大响应时长","最小响应时长","平均响应时长","90%响应时长"]},xAxis:{type:"category",data:this.xData},yAxis:{type:"value",name:"时间",nameGap:"10",axisLabel:{formatter:"{value} s"}},series:[{name:"最大响应时长",type:"bar",barGap:0,data:this.max},{name:"最小响应时长",type:"bar",barGap:0,data:this.min},{name:"平均响应时长",type:"line",data:this.average},{name:"90%响应时长",type:"line",data:this.percent}]};this.value&&(t.title={text:"URL: "+this.value}),this.myChart.setOption(t)}}}},344:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var s=a(16),i=a(296),r=a.n(i),n=a(290),l=a(291),o=a(168),u=a.n(o),c=a(293),d=(a.n(c),a(294)),h=(a.n(d),a(295)),v=(a.n(h),a(289),a(288),a(292)),p=a.n(v);s.default.prototype.$echarts=p.a,s.default.config.productionTip=!1,s.default.use(u.a),new s.default({el:"#app",router:n.a,store:l.a,template:"<App/>",components:{App:r.a}}),n.a.beforeEach(function(t,e,a){a()})},345:function(t,e){},346:function(t,e,a){"use strict";Object.defineProperty(e,"__esModule",{value:!0}),a.d(e,"count",function(){return s});var s=function(t){return t.count}},347:function(t,e,a){"use strict";a.d(e,"a",function(){return s});var s="ADD"},348:function(t,e,a){"use strict";var s=a(355),i=a.n(s),r=a(347),n=i()({},r.a,function(t){t.count++});e.a=n},349:function(t,e,a){"use strict";var s={count:0};e.a=s},677:function(t,e){},678:function(t,e){},679:function(t,e){},680:function(t,e){},681:function(t,e){},682:function(t,e){},695:function(t,e,a){function s(t){a(677)}var i=a(64)(a(339),a(700),s,"data-v-272c0f1d",null);t.exports=i.exports},696:function(t,e,a){function s(t){a(678)}var i=a(64)(a(340),a(701),s,"data-v-273a269e",null);t.exports=i.exports},697:function(t,e,a){function s(t){a(679)}var i=a(64)(a(341),a(702),s,"data-v-27483e1f",null);t.exports=i.exports},698:function(t,e,a){function s(t){a(680)}var i=a(64)(a(342),a(703),s,"data-v-275655a0",null);t.exports=i.exports},699:function(t,e,a){function s(t){a(681)}var i=a(64)(a(343),a(704),s,"data-v-27646d21",null);t.exports=i.exports},700:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"body-wrapper"},[a("div",{staticClass:"title-wrapper"},[t._v("实时访问情况")]),t._v(" "),a("div",{staticClass:"select-wrapper"},[a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("来源IP：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-select",{attrs:{placeholder:"请选择来源IP",clearable:"",size:"small"},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}},t._l(t.list,function(t,e){return a("el-option",{key:e,attrs:{value:t}})}),1)],1)]),t._v(" "),a("div",{staticClass:"select-button",on:{click:t.searchButton}},[t._v("查询")])]),t._v(" "),a("div",{staticClass:"chart-wrapper"},[a("div",{ref:"myChart",staticStyle:{height:"400px"}})])])},staticRenderFns:[]}},701:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"body-wrapper"},[a("div",{staticClass:"title-wrapper"},[t._v("目标IP统计")]),t._v(" "),a("div",{staticClass:"select-wrapper"},[a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("时间范围：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-date-picker",{attrs:{type:"datetimerange",format:"yyyy/MM/dd HH:mm","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",size:"small"},on:{change:t.dateChange},model:{value:t.dateValue,callback:function(e){t.dateValue=e},expression:"dateValue"}})],1)]),t._v(" "),a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("目标IP：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-select",{attrs:{placeholder:"请选择目标IP",clearable:"",size:"small"},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}},t._l(t.list,function(t,e){return a("el-option",{key:e,attrs:{value:t}})}),1)],1)]),t._v(" "),a("div",{staticClass:"select-button",on:{click:t.getData}},[t._v("查询")])]),t._v(" "),a("div",{staticClass:"chart-wrapper"},[a("div",{ref:"myChart",staticStyle:{height:"400px"}})])])},staticRenderFns:[]}},702:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"body-wrapper"},[a("div",{staticClass:"title-wrapper"},[t._v("来源IP统计")]),t._v(" "),a("div",{staticClass:"select-wrapper"},[a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("时间范围：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-date-picker",{attrs:{type:"datetimerange",format:"yyyy/MM/dd HH:mm","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",size:"small"},on:{change:t.dateChange},model:{value:t.dateValue,callback:function(e){t.dateValue=e},expression:"dateValue"}})],1)]),t._v(" "),a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("来源IP：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-select",{attrs:{placeholder:"请选择来源IP",clearable:"",size:"small"},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}},t._l(t.list,function(t,e){return a("el-option",{key:e,attrs:{value:t}})}),1)],1)]),t._v(" "),a("div",{staticClass:"select-button",on:{click:t.getData}},[t._v("查询")])]),t._v(" "),a("div",{staticClass:"chart-wrapper"},[a("div",{ref:"myChart",staticStyle:{height:"400px"}})])])},staticRenderFns:[]}},703:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"body-wrapper"},[a("div",{staticClass:"title-wrapper"},[t._v("URL请求统计")]),t._v(" "),a("div",{staticClass:"select-wrapper"},[a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("时间范围：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-date-picker",{attrs:{type:"datetimerange",format:"yyyy/MM/dd HH:mm","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",size:"small"},on:{change:t.dateChange},model:{value:t.dateValue,callback:function(e){t.dateValue=e},expression:"dateValue"}})],1)]),t._v(" "),a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("URL：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-select",{attrs:{placeholder:"请选择URL",clearable:"",size:"small"},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}},t._l(t.list,function(t,e){return a("el-option",{key:e,attrs:{value:t}})}),1)],1)]),t._v(" "),a("div",{staticClass:"select-button",on:{click:t.getData}},[t._v("查询")])]),t._v(" "),a("div",{staticClass:"chart-wrapper"},[a("div",{ref:"myChart",staticStyle:{height:"400px"}})])])},staticRenderFns:[]}},704:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"body-wrapper"},[a("div",{staticClass:"title-wrapper"},[t._v("URL响应统计")]),t._v(" "),a("div",{staticClass:"select-wrapper"},[a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("时间范围：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-date-picker",{attrs:{type:"datetimerange",format:"yyyy/MM/dd HH:mm","range-separator":"至","start-placeholder":"开始日期","end-placeholder":"结束日期",size:"small"},on:{change:t.dateChange},model:{value:t.dateValue,callback:function(e){t.dateValue=e},expression:"dateValue"}})],1)]),t._v(" "),a("div",{staticClass:"select-group"},[a("span",{staticClass:"select-group-title"},[t._v("URL：")]),t._v(" "),a("div",{staticClass:"select-group-item"},[a("el-select",{attrs:{placeholder:"请选择URL",clearable:"",size:"small"},model:{value:t.value,callback:function(e){t.value=e},expression:"value"}},t._l(t.list,function(t,e){return a("el-option",{key:e,attrs:{value:t}})}),1)],1)]),t._v(" "),a("div",{staticClass:"select-button",on:{click:t.getData}},[t._v("查询")])]),t._v(" "),a("div",{staticClass:"chart-wrapper"},[a("div",{ref:"myChart",staticStyle:{height:"400px"}})])])},staticRenderFns:[]}},705:function(t,e){t.exports={render:function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{attrs:{id:"app"}},[a("div",{staticClass:"menu-wrapper"},[a("el-menu",{staticClass:"menu-group",attrs:{router:"","default-active":t.curPath}},[a("el-menu-item",{attrs:{index:"/"}},[a("i",{staticClass:"el-icon-time"}),t._v(" "),a("span",{attrs:{slot:"title"},slot:"title"},[t._v("实时监控")])]),t._v(" "),a("el-menu-item",{attrs:{index:"/b"}},[a("i",{staticClass:"el-icon-menu"}),t._v(" "),a("span",{attrs:{slot:"title"},slot:"title"},[t._v("目标IP")])]),t._v(" "),a("el-menu-item",{attrs:{index:"/c"}},[a("i",{staticClass:"el-icon-menu"}),t._v(" "),a("span",{attrs:{slot:"title"},slot:"title"},[t._v("源IP")])]),t._v(" "),a("el-menu-item",{attrs:{index:"/d"}},[a("i",{staticClass:"el-icon-tickets"}),t._v(" "),a("span",{attrs:{slot:"title"},slot:"title"},[t._v("URL请求错误")])]),t._v(" "),a("el-menu-item",{attrs:{index:"/e"}},[a("i",{staticClass:"el-icon-tickets"}),t._v(" "),a("span",{attrs:{slot:"title"},slot:"title"},[t._v("URL请求响应")])])],1)],1),t._v(" "),a("div",{staticClass:"content-wrapper"},[a("router-view")],1)])},staticRenderFns:[]}}},[344]);
//# sourceMappingURL=app.ccf220c4a231582475aa.js.map