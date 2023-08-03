<template>
  <div style="margin: 5% 2%">
    <el-form size="default" label-position="left" ref="form" width="100%" :model="book.book" :rules="rule">
      <div style="width: 55%;float: left">
        <el-row gutter="2" justify="space-evenly">
          <el-col span="10">
            <el-form-item label="编号">
              <el-input v-model="book.book.bookId" disabled></el-input>
            </el-form-item>
            <el-form-item label="书名" prop="bookName">
              <el-input v-model="book.book.bookName"></el-input>
            </el-form-item>
            <el-form-item label="作者">
              <el-input v-model="book.book.bookAuthor"></el-input>
            </el-form-item>
            <el-form-item label="价格">
              <el-input v-model="book.book.bookPrice"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系列" prop="seriesName">
              <el-select v-model="book.book.seriesName"  placeholder="请选择">
                <el-option v-for="item in series" :key="item.seriesId" :label="item.seriesName"
                           :value="item.seriesName"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="出版社">
              <el-input v-model="book.book.bookFactory" ></el-input>
            </el-form-item>
            <el-form-item label="发售日期">
              <el-date-picker v-model="book.book.bookAddDate" type="date" placeholder="选择日期 " format="YYYY/MM/DD"
                              value-format="YYYY-MM-DD"/>
            </el-form-item>
            <el-form-item label="数量" prop="bookNum">
              <el-input v-model="book.book.bookNum"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- <el-row> -->
        <!-- <el-form-item label="简介" prop="bookInfo" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookInfo" prop="book.book.bookInfo" clearable
              :autosize="{ minRows: 2, maxRows: 4 }" maxlength="1000" placeholder="Please input"
              show-word-limit></el-input>
          </el-form-item> -->
        <!-- </el-row> -->

        <!-- <el-row>
          <el-form-item label="详情" style="width:90%;padding-left:4%">
          </el-form-item>
        </el-row> -->

        <el-collapse accordion>
          <el-collapse-item title="简介" name="1" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookInfo" prop="book.book.bookInfo" clearable
                      :autosize="{ minRows: 2, maxRows: 4 }" maxlength="100" placeholder="Please input"
                      show-word-limit></el-input>
          </el-collapse-item>
          <el-collapse-item title="详情" name="2" style="width:90%;padding-left:4%">
            <el-input type="textarea" v-model="book.book.bookDetail" prop="book.book.bookInfo"
                      :autosize="{ minRows: 2, maxRows: 4 }" clearable maxlength="1000" placeholder="Please input"
                      show-word-limit></el-input>
          </el-collapse-item>

        </el-collapse>

        <el-row justify="space-evenly">
          <el-col span="24">
            <el-button type="primary" plain round @click="alter()">修改</el-button>
          </el-col>
          <el-col span="24">
            <el-button type="success" plain round @click="back()">返回</el-button>
          </el-col>
        </el-row>

      </div>


      <div style="width: 40%;float: right;margin-right: 1%">
        <el-row v-show="havePicture">
          <el-col :span="15">
            <el-image :src='book.book.bookPicture' style="width: 100%; height: 90%" v-show="havePicture"></el-image>
          </el-col>
          <el-col :span="9">
            <el-button type="warning" plain round v-show="havePicture"><a href="pict/test/download">下载图片</a>
            </el-button>
            <el-button @click="havePicture = !havePicture" plain round type="primary" v-show="havePicture">清空图片
            </el-button>
          </el-col>
        </el-row>

        <el-row v-show="!havePicture">
          <el-col>
            <el-upload class=" upload-demo" drag action="pict/test/up" multiple limit="100"
                       encytype="multipart/form-data"
                       ref="pict" name="photo" v-show="!havePicture" @keydown.y="handleBookPicture()">
              <el-icon class="el-icon--upload">
                <upload-filled/>
              </el-icon>
              <div class="el-upload__text">
                <em>图片拖入</em> 或<em>点击上传</em>
                <p> 确定后点击 Y 键可查看上传的图片</p>
                重新上传图片请先清空列表
              </div>
              <template #tip>
                <div class="el-upload__tip">
                  只能上传图片
                </div>
              </template>
            </el-upload>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script setup>
import {useRouter} from "vue-router";
import {reactive, onBeforeMount, toRef, ref} from "vue";
import {UploadFilled} from '@element-plus/icons-vue'
import {getPicture} from "@/api/ImgAndExcelApi";
import {ElButton, ElIcon, ElMessage, ElNotification, ElUpload} from "element-plus";
import {alterBook, getBooksById, getSeries} from "@/api/BookApi";

let router = useRouter()
const prop = defineProps(['bookId'])
let bookId = toRef(prop, 'bookId')
console.log(bookId.value + "!!!!!!!!!!!!!!!!!!!!!!")

let havePicture = ref(true);
let handleBookPicture = (() => {
  setTimeout(() => {
    getPicture().then(Response => {
      book.book.bookPicture = Response.data
      if (book.book.bookPicture != null && book.book.bookPicture != "")
        console.log(book.book.bookPicture + "图片")
      havePicture.value = true
    }).catch(Error => {
      ElMessage.Error(Error.data.message)
      console.log(Error)
    })
  }, 500);
})

let form = ref('')
const rule = reactive({
  bookName: [{
    required: true,
    message: '请输入书籍名称',
    trigger: 'blur'
  }],
  seriesName: [{
    required: true,
    message: '请输入书籍所属系列',
    trigger: 'blur'
  }],
  bookNum: [{
    required: true,
    message: '请输入书籍数量',
    trigger: 'blur'
  }],
})


const book = reactive({
  book: {
    bookName: "",
    bookAddDate: "",
    bookAuthor: "",
    bookFactory: "",
    bookId: "",
    bookNum: "",
    bookPicture: "",
    bookPrice: "",
    seriesName: "",
    bookInfo: "",
    bookDetail: ""
  }
})

let series = ref('')
onBeforeMount(async () => {
  await getBooksById(bookId.value).then(Response => {
    book.book = Response.data
    console.log(book.book.bookName + "@@@@@@@@@@@@@@@@@@@@@@@@@@@")
  })
  await getSeries().then(Response => {
    series.value = Response.data
    console.log(series.value)
  }).catch(Error => {
    ElMessage.Error(Error.data.message)
    console(Error)
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
      ElMessage.Error(Error.data.message)
      console(Error)
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
