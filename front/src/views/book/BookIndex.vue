<template>
  <div style="width:100%;height: 100%;padding: 5% 0">
    <el-carousel style="width:100%;height:100%" height="550px" :interval=1500 :initial-index=1 direction="horizontal"
                 type="card" :autoplay="true" arrow="hover" pause-on-hover loop indicator-position="outside"
                 trigger="hover">
      <el-carousel-item v-for="item in books" :key="item.bookId"
                        style="width: 30%;height: 100%;z-index:1;margin-left: 13%;">
        <el-image :src=item.bookPicture style="width: 100%;height: 100%" @click="inf(item.bookId)"></el-image>
      </el-carousel-item>
    </el-carousel>
  </div>

  <div style="width: 100%;height: 100%">
    <el-row :gutter="16">
      <el-col title="按系列查询" :span="8" :offset="5">
        <el-form-item label="系列">
          <el-select v-model="seriesName" clearable placeholder="请选择系列" @change="selectBySeries">
            <el-option v-for="item in series" :key="item.seriesId" :label="item.seriesName"
                       :value="item.seriesName"></el-option>
          </el-select>
          <el-icon class="is-loading" size="large" style="margin: 0 5px">
            <Football/>
          </el-icon>
        </el-form-item>
      </el-col>

      <el-col title="按书名查询" :span="5">
        <el-form-item label="书籍">
          <el-input v-model="searchName" type="text" clearable placeholder="请输入书名"/>
        </el-form-item>
      </el-col>

      <el-col :span="4">
        <el-button type="primary" @click="selectByName" plain round>查询
          <el-icon class="is-loading" size="large" style="margin: 0 5px">
            <Aim/>
          </el-icon>
        </el-button>
      </el-col>
    </el-row>

  </div>


  <div class="wrap">
    <div class="card_wrap">
      <div class="card_item" v-for="item in books" :key="item.book">
        <div class="card_content">
          <el-row>
            <el-row>
              <el-image :src="item.bookPicture" style="width: 90%;height: 100%;" @click="inf(item.bookId)"></el-image>
            </el-row>
            <el-tooltip :content="item.bookInfo" effect="dark">
              <el-row style="padding:8% 0">
                <el-col :span="24" @click="inf(item.bookId)">
                  <b @click="inf(item.bookId)">{{ item.bookName }}</b>
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

  <div style="width: 100%;height: 100%">
    <el-row :gutter="16">
      <el-col title="按系列查询" :span="8" :offset="5">
        <el-form-item label="系列">
          <el-select v-model="seriesName" clearable placeholder="请选择系列" @change="selectBySeries">
            <el-option v-for="item in series" :key="item.seriesId" :label="item.seriesName"
                       :value="item.seriesName"></el-option>
          </el-select>
          <el-icon class="is-loading" size="large" style="margin: 0 5px">
            <Football/>
          </el-icon>
        </el-form-item>
      </el-col>

      <el-col title="按书名查询" :span="5">
        <el-form-item label="书籍">
          <el-input v-model="searchName" type="text" clearable placeholder="请输入书名"/>
        </el-form-item>
      </el-col>

      <el-col :span="4">
        <el-button type="primary" @click="selectByName" plain round>查询
          <el-icon class="is-loading" size="large" style="margin: 0 5px">
            <Aim/>
          </el-icon>
        </el-button>
      </el-col>
    </el-row>

  </div>
  <el-table stripe :data="books" :highlight-current-row=true height="100%" style="width: 100%">
    <el-table-column prop="bookName" class-name="bookName" label="书名"></el-table-column>
    <el-table-column prop="bookPicture" label="封面">
      <template v-slot="scope">
        <el-image :src="scope.row.bookPicture" style="width: 80%;height: 100%;"
                  @click="inf(scope.row.bookId)"></el-image>
      </template>
    </el-table-column>
    <!--    <el-table-column prop="bookInfo" label="简介"></el-table-column>-->
    <!--    <el-table-column  prop="bookId" label="书籍编号" width="120px"></el-table-column>-->
    <el-table-column prop="bookAuthor" label="作者"></el-table-column>
    <el-table-column prop="seriesName" label="系列"></el-table-column>
    <el-table-column prop="bookPrice" label="价格" sortable></el-table-column>
    <!--    <el-table-column prop="bookAddDate" label="发布日期" sortable></el-table-column>-->
    <el-table-column prop="bookFactory" label="出版社"></el-table-column>
    <el-table-column prop="bookNum" label="库存" sortable></el-table-column>
    <el-table-column prop="bookId" label="操作">
      <template v-slot="scope">
        <el-button class="Ybutton" plain round type="success" @click="inf(scope.row.bookId)">书籍详情</el-button>
        <el-button type="primary" plain round @click="buy(scope.row.bookId)" v-show="scope.row.bookNum > 0">加入购物车
        </el-button>
      </template>
    </el-table-column>
  </el-table>


  <div>
    <el-steps :active="active" align-center finish-status="success" style="width: 70%;margin:3% 15%">
      <el-step title="Step 1" description="注册账户"/>
      <el-step title="Step 2" description="选择书籍加入购物车"/>
      <el-step title="Step 3" description="在购物车进行支付"/>
      <el-step title="Step 4" description="确认收货！"/>
    </el-steps>
    <el-button type="danger" style="width: 70%;margin:3% 15%" @click="next" plain round>下一步</el-button>
  </div>
</template>

<script setup>
import {reactive, ref, onBeforeMount} from "vue";
import {
  useRouter
} from "vue-router";
import {
  useStore
} from "vuex";
import {ElMessage, ElNotification} from "element-plus";
import {Aim, Football} from "@element-plus/icons-vue";
import {getBooks, getBooksByName, getSelectBySeries, getSeries} from "@/api/BookApi";
import {addOrder} from "@/api/OrderApi";

let books = ref([])
let active = ref(0)
let router = useRouter()
let store = useStore()

let next = (() => {
  active.value++ > 3 ? active.value = 0 : active.value
})

let searchName = ref('')
let selectByName = (() => {
  getBooksByName(searchName.value).then(Response => {
    books.value = Response.data
    searchName.value = ''
  })
})

let series = ref('')
onBeforeMount(async () => {
  await getSeries().then(Response => {
    series.value = Response.data
  })
})

let seriesName = ref('')
let selectBySeries = (() => {
  getSelectBySeries(seriesName.value).then(Response => {
    books.value = Response.data
  })
})

getBooks().then(Response => {
  books.value = Response.data
})

let inf = ((v) => {
  console.log(v)
  let id = v
  router.push({
    name: 'information',
    query: {
      id: id
    }
  })
})

let bag = reactive({
  bookId: '',
  userId: '',
  bookNum: 1
})

let buy = ((v) => {
  bag.bookId = v;
  console.log(v + "----左右两边都是bookId,左边是传过来的,右边是要发送后端的" + bag.bookId);
  bag.userId = store.state.userId;
  console.log(bag.userId);
  // 判断用户登陆了没，没登陆返回登录页面
  if (store.state.userId == "" || store.state.userId == undefined || store.state.userId == null) {
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
  justify-content: center;
  height: 100%;
  padding: 8px 0;
}

.card_wrap {
  display: flex;
  flex-wrap: wrap;
  width: 90%;
}

.card_item {
  flex-basis: 20%;
  margin-bottom: 6%;
  padding: 0 3%;
  box-sizing: border-box;
}

.card_content {
  background-color: rgb(247, 247, 247);
  border-radius: 2%;
  height: 60%;
  width: 80%;
}


/deep/ .bookName .cell {
  padding-left: 25%;
}

.Ybutton {
  margin-left: 10%;
  margin-top: 2%;
  margin-bottom: 10%;
}


</style>