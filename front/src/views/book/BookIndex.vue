<template>
  <div class="top"></div>
  <div style="width:100%;height: 100%;padding: 5% 0">
    <el-carousel style="width:100%;height:100%" height="550px" :interval=1500 :initial-index=1 direction="horizontal"
                 type="card" :autoplay="true" arrow="hover" pause-on-hover loop indicator-position="outside"
                 trigger="hover">
      <el-carousel-item v-for="item in books" :key="item.id"
                        style="width: 25%;height: 100%;z-index:1;margin-left: 12%;">
        <el-image :src="item.image?item.image.picture:`img/未设置图片时的404.jpg`" style="width: 100%;height: 100%"
                  @click="inf(item.id)"></el-image>
      </el-carousel-item>
    </el-carousel>
  </div>

  <div class="wrap">
    <div class="card_wrap">
      <div class="card_item" v-for="item in books" :key="item.book">
        <div class="card_content">
          <el-row>
            <el-row>
              <el-image :src="item.image?item.image.picture:`img/未设置图片时的404.jpg`"
                        style="width: 100%;height: 100%;" @click="inf(item.id)"></el-image>
            </el-row>
            <el-tooltip :content="item.word.bookDescribe" effect="dark">
              <el-row style="padding:8% 0">
                <el-col :span="24" @click="inf(item.id)">
                  <b @click="inf(item.id)">{{ item.bookName }}</b>
                </el-col>
                <el-col :span="18" style="padding:5% 0">
                  <el-tag type="danger" class="mx-1" effect="dark" plain>{{ item.bookPrice }}￥/本</el-tag>
                </el-col>
              </el-row>
            </el-tooltip>
            <el-row :gutter="10">
              <el-col :span="18">
                {{ item.bookAuthor }}
              </el-col>
              <el-col :span="4">
                <el-tag type="warning" class="mx-1" effect="dark" round>
                  Auor
                </el-tag>
              </el-col>
            </el-row>
          </el-row>

        </div>
      </div>
    </div>
  </div>

  <el-table stripe :data="books" :highlight-current-row=true height="100%" style="width: 100%">
    <el-table-column type="expand">
      <template #default="props">
        <div style="float: left;margin-top:4%;width:10%;left: 10%;position:relative;">
          <el-image :src="props.row.image?props.row.image.picture:`img/未设置图片时的404.jpg`"
                    style="width: 100%; height: 100%"></el-image>
        </div>
        <div style="float: right;width:70%;margin: 2% 2% 2% 5%">
          <p><b>书籍编号:</b> {{ props.row.id }}</p>
          <p><b>出版社:</b> {{ props.row.bookFactory }}</p>
          <p><b>发布日期:</b> {{ props.row.bookAddDate }}</p>
          <p><b>上架日期:</b> {{ props.row.bookSignTime }}</p>
          <p><b>更新日期:</b> {{ props.row.version }}</p>
          <p v-if="props.row.word.bookDescribe"><b>书籍简介:</b> {{ props.row.word.bookDescribe }}</p>
          <p v-if="props.row.word.bookDetail"><b>书籍详情:</b></p> {{ props.row.word.bookDetail }}
        </div>
      </template>
    </el-table-column>
    <el-table-column prop="bookName" class-name="bookName" label="书名" align="center" :min-width="80"
                     :show-overflow-tooltip="true"></el-table-column>
    <el-table-column prop="image.picture" label="封面">
      <template v-slot="scope">
        <el-image :src="scope.row.image?scope.row.image.picture:`img/未设置图片时的404.jpg`"
                  style="width: 80%;height: 100%;"
                  @click="inf(scope.row.id)"></el-image>
      </template>
    </el-table-column>
    <el-table-column prop="bookAuthor" label="作者"></el-table-column>
    <el-table-column prop="bookPrice" label="价格">
      <template v-slot="scope">
        <el-tag type="danger" effect="dark">{{ scope.row.bookPrice }}￥</el-tag>
      </template>
    </el-table-column>
    <el-table-column prop="series.seriesName" label="系列"></el-table-column>
    <!--    <el-table-column prop="bookAddDate" label="发布日期" sortable></el-table-column>-->
    <el-table-column prop="bookFactory" label="出版社"></el-table-column>
     <el-table-column prop="bookNumber" label="库存" sortable>
       <template v-slot="scope">
         在售： {{scope.row.bookNumber}} 本
       </template>
     </el-table-column>
    <el-table-column prop="id" label="操作">
      <template v-slot="scope">
        <el-row justify="space-around">
          <el-col :span="10">
            <el-button  plain round type="success" @click="inf(scope.row.id)">详情</el-button>
          </el-col>
          <el-col :span="14">
            <el-button type="primary" plain round
                       @click="buy(scope.row.id,scope.row.discount,scope.row.bookPrice,scope.row.kickback,scope.row.expressFare)"
                       v-show="scope.row.bookNumber > 0">加入购物车
            </el-button
            ></el-col>
        </el-row>
      </template>
    </el-table-column>
  </el-table>


  <div style="width: 70%;margin:3% 15%">
    <el-row justify="space-evenly">
      <el-col :span="12" style="margin-top: 5%">
        <el-pagination
            v-model:current-page="pageNo"
            v-model:page-size="pageSize"
            :page-sizes="[5, 10, 15,20,30,50,100,200,400,1000]"
            background
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
        />
      </el-col>
      <el-col :span="12">
        <el-steps :active="active" align-center finish-status="success" style="width: 70%;margin:3% 15%">
          <el-step title="Step 1" description="注册账户"/>
          <el-step title="Step 2" description="选择书籍加入购物车"/>
          <el-step title="Step 3" description="在购物车进行支付"/>
          <el-step title="Step 4" description="确认收货！"/>
        </el-steps>
        <el-button type="danger" style="width: 70%;margin:3% 15%" @click="next" plain round>下一步</el-button>
      </el-col>
    </el-row>


  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {ElMessage, ElNotification} from "element-plus";
import {getBooks, selectBySeries} from "@/api/BookApi";
import {addOrder} from "@/api/OrderApi";
import emitter from "@/utils/bus";
// 监听事件，导航栏更改后这里就会触发，书记页面就会根据在导航页面选择的书籍系列展示书籍
let pageNo = ref(1)
let pageSize = ref(5)
let total = ref(1)

let seriesNameChange=ref("null");
emitter.on('seriesChange', data => {
  console.log(data)
  seriesNameChange.value=data
  selectBySeries(data,1, pageSize.value).then(Response => {
    books.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})

// 当用户选择完系列重新点击首页后进行全部书籍的检查
emitter.on('tooooBookRestart', v => {
  getBooks("null", 1, pageSize.value).then(Response => {
    console.log(v)
    seriesNameChange.value="null"
    books.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})


let books = ref([])
let active = ref(0)
let router = useRouter()
let store = useStore()

let next = (() => {
  active.value++ > 3 ? active.value = 0 : active.value
})


onMounted(async () => {
  await getBooks("null", pageNo.value, pageSize.value).then(Response => {
    books.value = Response.data.data
    pageSize.value = Response.data.pageSize
    total.value = parseInt(Response.data.total)
    pageNo.value = Response.data.current
  })
})

let handleSizeChange = ((val) => {
  if (seriesNameChange.value==="null"){
    getBooks("null", 1, val).then(Response => {
      books.value = Response.data.data
      pageSize.value = Response.data.pageSize
      total.value = parseInt(Response.data.total)
      pageNo.value = Response.data.current
    })
  }else {
    selectBySeries(seriesNameChange.value, pageNo.value, val).then(Response => {
      books.value = Response.data.data
      pageSize.value = Response.data.pageSize
      total.value = parseInt(Response.data.total)
      pageNo.value = Response.data.current
    })
  }

})

let handleCurrentChange = ((val) => {
  if (seriesNameChange.value==="null") {
    getBooks("null", val, pageSize.value).then(Response => {
      books.value = Response.data.data
      pageSize.value = Response.data.pageSize
      total.value = parseInt(Response.data.total)
      pageNo.value = Response.data.current
    })
  }else{
    selectBySeries(seriesNameChange.value, val, pageSize.value).then(Response => {
      books.value = Response.data.data
      pageSize.value = Response.data.pageSize
      total.value = parseInt(Response.data.total)
      pageNo.value = Response.data.current
    })
  }
})

let inf = ((v) => {
  console.log(v)
  router.push({
    name: 'information',
    query: {
      id: v
    }
  })
})
// 首页默认购买一本
let bag = reactive({
  bookId: '',
  userId: '',
  buyNumber: 1,
  discount: 0,
  bookPrice: 0,
  kickback: 0,
  expressFare: 0
})

let buy = ((v, discount, bookPrice, kickback, expressFare) => {
  bag.bookId = v;
  bag.userId = store.state.userId;
  bag.discount = discount;
  bag.bookPrice = bookPrice;
  bag.kickback = kickback;
  bag.expressFare = expressFare
  // 判断用户登陆了没，没登陆返回登录页面
  if (store.state.userId === "" || store.state.userId === undefined || store.state.userId === null) {
    router.push({
      name: 'login'
    })
  } else {
    let addBag = JSON.stringify(bag)
    addOrder(addBag).then(Response => {
      console.log(Response.message)
      ElMessage.success(Response.message)
      ElNotification.success({
        title: Response.message,
        message: Response.message + "可前往购物车查看",
        position: "bottom-right"
      })
      // router.push({
      //   name: "userOrder",
      // });
    }).catch(Error => {
      ElMessage.error(Error.data.message)
      console.log(Error)
    })
  }
})
</script>
<style scoped>

.wrap {
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: space-evenly;
  height: 100%;
  padding: 8px 0;
}

.card_wrap {
  flex-direction: row;
  justify-content: space-evenly;
  display: flex;
  flex-wrap: wrap;
  width: 90%;
}


.card_item {
  flex-basis: 20%;
  margin-bottom: 6%;
  padding: 0 2%;
  box-sizing: border-box;
}

.card_content {
  background-color: rgb(253, 253, 253);
  border-radius: 2%;
  box-shadow: 2em 2em 2em #8fcbee;
  height: 50%;
  width: 70%;
}


.top {
  left: 0;
  height: 100%;
  width: 100%;
  position: fixed;
  margin: 0 0;
  background: linear-gradient(115deg, #bbcfe0 40%, #efe7e7 70%);
  z-index: -1;
}
</style>