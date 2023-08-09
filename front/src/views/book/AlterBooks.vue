<template>
  <div id="building">
    <div style="margin-top: 5%;left: 2%;width:55%;height:40%;position: relative">
      <el-form  label-position="top" ref="form" :model="book.book" :rules="rules" :inline="true" label-width="355" >
        <el-form-item label="书名" prop="bookName">
          <el-input v-model="book.book.bookName"></el-input>
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="book.book.bookAuthor"></el-input>
        </el-form-item>
        <el-form-item label="价格(元)" prop="bookPrice">
          <el-input-number :step=1 :min=0 :max=1000000 v-model="book.book.bookPrice"></el-input-number>
        </el-form-item>
        <el-form-item label="折扣 ((折扣/10)*100%)" prop="discount">
          <el-input-number :step=0.01 :min=0 :max=10 v-model="book.book.discount"></el-input-number>
        </el-form-item>
        <el-form-item label="系列" prop="seriesId">
          <el-select v-model="book.book.seriesId" placeholder="请选择">
            <el-option v-for="item in series" :key="item.id" :label="item.seriesName"
                       :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出版社">
          <el-input v-model="book.book.bookFactory"></el-input>
        </el-form-item>
        <el-form-item label="发售日期">
          <el-date-picker v-model="book.book.bookAddDate" type="date" placeholder="选择日期 " format="YYYY/MM/DD"
                          value-format="YYYY-MM-DD"/>
        </el-form-item>
        <el-form-item label="上架数量" prop="bookNumber">
          <el-input-number :step=1 :min=0 :max=1000000 v-model="book.book.bookNumber"></el-input-number>
        </el-form-item>
        <el-form-item label="平台回扣（抽取比例）" v-if="store.state.isAdmin">
          <el-input-number :step="0.01" :min=0 :max=1 v-model="book.book.kickback"></el-input-number>
        </el-form-item>
        <el-form-item label="运费(元/Km)" prop="expressFare">
          <el-input-number :step=1 :min=0 :max=1000000 v-model="book.book.expressFare"></el-input-number>
        </el-form-item>
        <el-form-item label="简介" prop="word.bookDescribe" style="top:90%;position: absolute;width: 100%;">
          <el-input type="textarea" v-model="book.book.word.bookDescribe"  clearable prop="book.book.bookDescribe"
                    :autosize="{ minRows: 5, maxRows: 7 }" maxlength="10000" placeholder="简短介绍一下！"
                    show-word-limit></el-input>
        </el-form-item>

        <el-form-item label="详情" prop="word.bookDetail" style="top:150%;position: absolute;width: 100%;">
          <el-input type="textarea" v-model="book.book.word.bookDetail" prop="book.book.Detail"
                    :autosize="{ minRows: 5, maxRows: 7 }" clearable maxlength="10000" placeholder="书籍的特色"
                    show-word-limit></el-input>
        </el-form-item>
      </el-form>
    </div>


    <el-row  justify="space-evenly" style="left:33%;top:35%;margin: 2% 0;width: 30%;position:absolute;">
      <el-col :span="8">
        <el-button type="primary" style="width: 100%" size="large" plain round @click="alter()">修改</el-button>
      </el-col>
      <el-col :span="8">
        <el-button type="success" style="width: 100%" size="large" plain round @click="back()">返回</el-button>
      </el-col>
    </el-row>

    <div style="right: 8%;width: 30%;top:10%;position: absolute">
      <el-row>
        <el-row v-show="havePicture">
          <el-col :span="24">
            <el-image style="width: 100%; height: 80%;left:10%;position: relative"
                      :src="book.book.image.picture" fit="contain" v-show="havePicture"/>
          </el-col>

          <el-col :span="24">
            <el-row :gutter="10" style="float:right;left:40%;top:82%;width: 80%;position:absolute;">
              <el-col :span="8">
                <el-button plain round size="large" style="width: 100%" type="primary" v-show="havePicture"><a
                    href="picture/test/download">下载头像</a>
                </el-button>
              </el-col>
              <el-col :span="8">
                <el-button plain round size="large" style="width: 100%" type="warning"
                           @click="havePicture = !havePicture"
                           v-show="havePicture">清空头像
                </el-button>
              </el-col>
            </el-row>
          </el-col>

        </el-row>

        <el-row justify="space-around" v-show="!havePicture" style="margin-top: 10%;left: 20%">
          <el-upload ref="pict" class="upload-demo"
                     :action="`picture/test/up/${store.state.shopkeeperId?store.state.shopkeeperId:store.state.adminId}`"
                     multiple :limit="1"
                     encytype="multipart/form-data" name="photo" v-show="!havePicture" :auto-upload="false"
                     :show-file-list="true" :on-success="handleBookPicture"
          >
            <template #trigger>
              <el-col :span="2">
                <el-button dark round type="primary">上传头像，限制一张</el-button>
              </el-col>
            </template>
            <el-col :span="12">
              <el-button plain round type="success" @click="handleBookPicture">点击上传服务器</el-button>
            </el-col>
            <template #tip>
              <div class="el-upload__tip" STYLE="float:right;right:5%;color: #efe8e8;font-size: 20px">
                jpg/png files with a size less than 500kb
              </div>
            </template>
          </el-upload>
        </el-row>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, onBeforeMount, toRef, ref} from "vue";
import {getPicture} from "@/api/ImgAndExcelApi";
import {
  ElButton,
  ElDatePicker,
  ElForm,
  ElFormItem,
  ElInput,
  ElMessage,
  ElNotification,
  ElUpload
} from "element-plus";
import {alterBook, getBooksById} from "@/api/BookApi";
import {getAllSeries} from "@/api/SeriesApi";
import store from "@/store";

let router = useRouter()
// eslint-disable-next-line no-undef
const prop = defineProps(['bookId'])
let bookId = toRef(prop, 'bookId')

let havePicture = ref(true);
let pict = ref('')
let handleBookPicture = (() => {
  setTimeout(() => {
    pict.value.submit()
    pict.value.clearFiles()
    getPicture(store.state.shopkeeperId ? store.state.shopkeeperId : store.state.adminId).then(Response => {
      book.book.image.picture = Response.data
      if (book.book.image.picture != null && book.book.image.picture !== "")
        console.log(book.book.image.picture + "图片")
      havePicture.value = true
    }).catch(Error => {
      ElMessage.error(Error.data.message)
      console.log(Error)
    })
  }, 500);
})

let form = ref('')
const rules = reactive({
  bookName: [{
    required: true,
    message: '请输入书籍名称',
    trigger: 'blur'
  }],
  seriesId: [{
    required: true,
    message: '请输入书籍所属系列',
    trigger: 'blur'
  }],
  bookNumber: [{
    required: true,
    message: '请输入书籍数量',
    trigger: 'blur'
  }],
  bookPrice: [{
    required: true,
    message: '请输入书籍价格',
    trigger: 'blur'
  }],
  discount: [{
    required: true,
    message: '请输入折扣力度',
    trigger: 'blur'
  }],
  expressFare: [{
    required: true,
    message: '请输入基本运费',
    trigger: 'blur'
  }],
  'word.bookDescribe': [{
    required: true,
    message: '请输入书籍介绍',
    trigger: 'blur'
  }],
  'word.bookDetail': [{
    required: true,
    message: '请输入书籍详情',
    trigger: 'blur'
  }]
})

const book = reactive({
  book: {
    id: "",
    seriesId: "",
    shopkeeperId: '',
    bookName: "",
    bookAddDate: "",
    bookAuthor: "",
    bookFactory: "",
    bookNumber: 0,
    bookPrice: 0,
    discount: 1,
    expressFare: 0,
    kickback: 0.1,
    bookSignTime: "",
    image: {
      picture: ""
    },
    word: {
      bookDescribe: "",
      bookDetail: "",
    }
  },
});

let series = ref('')
onBeforeMount(async () => {
  await getBooksById(bookId.value).then(Response => {
    book.book = Response.data
  })
  await  getAllSeries(null, 1, 100).then(Response => {
    series.value = Response.data.data
  }).catch(Error => {
    ElMessage.error(Error.data.message)
    console.log(Error)
  })
})

let alter = (() => {
  form.value.validate(valid => {
    if (!valid) {
      ElNotification({
        title: '警告',
        message: '请检查输入的内容是否正确',
        type: 'warning',
        position: 'top-left'
      })
      return false
    }
    let Book = JSON.stringify(book.book)
    alterBook(Book).then(Response => {
      ElMessage.success(Response.message)
      router.push({
        name: 'adminBooks',
      })
    }).catch(Error => {
      ElMessage.error(Error.data.message)
      console.log(Error)
    })
  })
})

let back = (() => {
  router.push({
    name: 'adminBooks'
  })
})
</script>

<style scoped>
#building {
  bottom: 0;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  font-size: large;
  position: fixed;
  background-size: 100% 100%;
  background-color: rgba(112, 81, 66, 0.42);
  z-index: -1;
}
.el-radio-group {
  margin-right: 12px;
}
.el-input {
  width: 80%;
}

.el-form {
  margin-left: 70%;
  float: right;
  width: 100%;
}
</style>
