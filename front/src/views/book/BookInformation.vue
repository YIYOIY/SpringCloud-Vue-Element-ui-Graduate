<template>
  <div class="back">

    <div style="margin: 4% 10%">
      <el-row justify="space-evenly" :gutter="10">
        <el-col :span="12">
          <h2><span>{{ "<<" + book.book.bookName + '>>' }}</span></h2>
          <el-card :body-style="{ padding: '30px 50px' }" shadow="hover" style="width: 80%; align-content: center">
            <div class="info_div" style="color: #eeb20b">
              <span class="info_span">书名:</span><span>{{ book.book.bookName }}</span>
            </div>
            <div class="info_div">
              <span class="info_span" style="color: orangered">价格:</span><span
                style="color: orangered">RMB {{ book.book.bookPrice }} 元</span>
            </div>
            <div class="info_div" style="color: orangered">
              <span class="info_span">折扣:</span><span>{{ (book.book.discount / 10) * 100 }} % </span>
              <span class="info_span"
                    style="margin-left: 15%">运费:</span><span>{{
                book.book.expressFare > 0 ? book.book.expressFare : 0
              }} 元 </span>
            </div>
            <div class="info_div" style="color: #23af6a">
              <span class="info_span">作者:</span><span>{{ book.book.bookAuthor }}</span>
            </div>
            <div class="info_div" style="color: #176260">
              <span class="info_span">系列:</span><span>{{ book.book.series.seriesName }}</span>
            </div>
            <div class="info_div">
              <span class="info_span">出版社:</span><span>{{ book.book.bookFactory }}</span>
            </div>
            <div class="info_div">
              <span class="info_span">出版日期:</span><span style="color: deepskyblue">{{
                book.book.bookAddDate
              }}</span>
            </div>
            <h4 class="h3" style="float: left; width: 100%">
              <span style="margin-right: 20%">购买数量:</span>
              <el-slider v-model="number" show-input :max="book.book.bookNumber"/>
            </h4>
            <div style="text-align: center; margin-top: 2%">
              <el-button style=" float: left; width: 50%" type="success" @click="buy()"
                         v-show="number >= 1">加入购物车
              </el-button>
              <el-button type="primary" @click="back()">返回</el-button>
            </div>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card shadow="hover"
                   style="margin-top: 6%;width: 40%;height: 85%;align-content: center;text-align: center;position:absolute;">
            <el-image :src="book.book.image.picture" style="width:70%;"></el-image>
            <h6>图片仅供参考,具体以实物为准!</h6>
          </el-card>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="24">
          <h4 style="padding-left:5%;left: 5%;position:relative;background-color: #eaefef;color: #131212;box-shadow: -0.2em -0.2em 2em 0.2em #fdce4a;width: 10%;border-radius: 10px">
            书籍简介</h4>
          <el-card :body-style="{ padding: '20px 50px' }" shadow="hover" style="width: 100%; align-content: center">
            <b>{{ book.book.word.bookDescribe }}</b>
          </el-card>
        </el-col>
        <el-col :span="24">
          <h4 style="padding-left:5%;left: 5%;position:relative;color: #2f2e29;box-shadow: -0.2em -0.2em 2em 0.2em #50b652;width: 10%;border-radius: 10px">
            书籍详情</h4>
          <el-card :body-style="{ padding: '20px 50px' }" shadow="hover" style="width: 100%; align-content: center">
            <b>{{ book.book.word.bookDetail }}</b>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {useStore} from "vuex";
import {useRoute} from "vue-router";

let router = useRouter();
let route = useRoute();
let store = useStore();
import {reactive, ref} from "vue";
import {ElMessage, ElNotification} from "element-plus";
import {getBooksById} from "@/api/BookApi";
import {addOrder} from "@/api/OrderApi";

let isOrder = ref(false)
if (route.query.order) {
  isOrder.value = true
}
let number = ref(1)
// eslint-disable-next-line no-undef
const props = defineProps(["id"]);

// let book = ref(null)

let back = (() => {
  if (isOrder.value) {
    router.push({
      name: "userOrder"
    });
  } else
    router.push({
      name: "book"
    });
})

const book = reactive({
  book: {
    id: "",
    seriesId: "",
    shopkeeperId: "",
    imageId: "",
    wordId: "",
    bookName: "",
    bookAddDate: "",
    bookAuthor: "",
    bookFactory: "",
    bookNumber: 0,
    bookPrice: 0,
    discount: 0,
    expressFare: 0,
    kickback: 0,
    bookSignTime: "",
    shopkeeper: {
      id: "",
      shopkeeperName: "",
    },
    series: {
      id: '',
      seriesName: "",
    },
    image: {
      id: "",
      picture: ""
    },
    word: {
      id: '',
      bookDescribe: "",
      bookDetail: "",
    }
  },
});

getBooksById(props.id).then((res) => {
  console.log(res.data)
  book.book = res.data;
  if (res.code !== 200) {
    ElMessage({
      message: res.message,
      grouping: true,
      type: 'error',
      center: true,
      showClose: true,
    })
    router.push('/book')
  }
});


let bag = reactive({
  bookId: "",
  userId: "",
  bookNumber: "",
  discount: 0,
  bookPrice: 0,
  kickback: 0,
  expressFare: 0
});
// 购买操作
let buy = () => {
  bag.bookId = book.book.id;
  bag.userId = store.state.userId;
  bag.buyNumber = number.value;
  bag.discount = book.book.discount;
  bag.bookPrice = book.book.bookPrice;
  bag.kickback = book.book.kickback;
  bag.expressFare = book.book.expressFare
  if (
      store.state.userId === "" ||
      store.state.userId === undefined ||
      store.state.userId === null
  ) {
    router.push({
      name: "login",
    });
  } else {

    let addBag = JSON.stringify(bag);
    addOrder(addBag).then((Response) => {
      ElMessage.success(Response.message)
      ElNotification({
        title: '成功加入购物车',
        message: Response.message,
        type: 'success',
        position: 'bottom-left',
      })
      // if (confirm(Response.message + "是否前往购物车?")) {
      // 	router.push({
      // 		name: "userOrder",
      // 	});
      // } else {
      // 	router.push({
      // 		name: "book",
      // 	});
      // }
    }).catch((Error) => {
      ElMessage.error(Error.data.message)
      console.log(Error);
    });
  }
};
</script>

<style scoped>
.info_div {
  font-size: 20px;
  background: linear-gradient(45deg, white 10%, #63eeee 80%);
  margin-bottom: 8%;
}

.info_span {
  width: 30%;
  margin-right: 15%;
  background: linear-gradient(25deg, #e8f5ef 10%, #eaefef 80%);
}

.back {
  position: absolute;
  left: 0;
  right: 0;
  background: linear-gradient(25deg, #a0afea 40%, #a5edef 70%);
}
</style>