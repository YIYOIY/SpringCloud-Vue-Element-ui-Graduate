<template>
	<div>
		<!-- <p v-show="false">{{ book.book.bookName }}</p> -->
		<el-row>
			<el-col :span="12">
				<h2>书籍简介</h2>
				<el-card :body-style="{ padding: '50px 50px' }" shadow="hover" style="width: 80%; align-content: center">
					<div class="info_div">
						<span class="info_span">书名:</span><span>{{ book.book.bookName }}</span>
					</div>
					<div class="info_div">
						<span class="info_span">价格:</span><span style="color: orangered">{{ book.book.bookPrice }}</span>
					</div>
					<div class="info_div">
						<span class="info_span">作者:</span><span>{{ book.book.bookAuthor }}</span>
					</div>
					<div class="info_div">
						<span class="info_span">系列:</span><span>{{ book.book.seriesName }}</span>
					</div>
					<div class="info_div">
						<span class="info_span">出版社:</span><span>{{ book.book.bookFactory }}</span>
					</div>
					<div class="info_div">
						<span class="info_span">出版日期:</span><span style="color: deepskyblue">{{ book.book.bookAddDate
						}}</span>
					</div>
					<h4 class="h3" style="float: left; width: 100%">
						<span style="margin-right: 20%">购买数量:</span>
						<el-slider v-model="number" show-input :max="book.book.bookNum" />
					</h4>
					<div style="text-align: center; margin-top: 2%">
						<!-- <el-button @click="number++" type="info" round v-show="book.book.bookNum > number">加一</el-button> -->
						<!-- <el-button @click="number--" type="info" round v-if="number > 1">减一</el-button> -->

						<el-button style=" float: left; width: 50%" type="success" @click="buy()"
							v-show="book.book.bookNum >= 1">加入购物车</el-button>
						<el-button type="primary" @click="cancelBuy()">返回</el-button>
					</div>
				</el-card>
			</el-col>

			<el-col :span="12">
				<el-card shadow="hover" style="
            margin-top: 10%;
            width: 80%;
			height: 80%;
            align-content: center;
            text-align: center;
          ">
					<el-image :src="book.book.bookPicture" style="width:60%;"></el-image>
					<h6>图片仅供参考,具体以实物为准!</h6>
				</el-card>
			</el-col>
		</el-row>

		<el-row>
			<el-col :span="24">
				<h2>书籍简介</h2>
				<el-card :body-style="{ padding: '50px 50px' }" shadow="hover" style="width: 100%; align-content: center">
					<p>{{ book.book.bookInfo }}</p>
				</el-card>
			</el-col>
			<el-col :span="24">
				<h2>书籍详情</h2>
				<el-card :body-style="{ padding: '50px 50px' }" shadow="hover" style="width: 100%; align-content: center">
					<p>{{ book.book.bookDetail }}</p>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script setup>
import axios from "axios";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
let router = useRouter();
let store = useStore();
import { reactive, ref } from "vue";
import { ElNotification } from "element-plus";
let number = ref(1)
const props = defineProps(["id"]);

// let book = ref(null)
let cancelBuy = (() => {
	router.push({
		name: "book",
	});
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
		bookDetail: "",
		bookInfo: "",
	},
});

axios.get(`api/lookup?bookId=${props.id}`).then((res) => {
	book.book = res.data;
});
console.log(book);



let bag = reactive({
	bookId: "",
	userId: "",
	bookNum: "",
});


// 购买操作
let buy = () => {
	bag.bookId = book.book.bookId;
	bag.userId = store.state.userId;
	console.log(bag + "购买操作");
	if (
		store.state.userId == "" ||
		store.state.userId == undefined ||
		store.state.userId == null
	) {
		router.push({
			name: "login",
		});
	} else {
		bag.bookNum = number.value;
		let addBag = JSON.stringify(bag);
		axios
			.post(`api/order`, addBag, {
				headers: {
					"Content-Type": "application/json",
				},
			})
			.then((Response) => {
				let message = Response.data;
				ElNotification({
					title: '成功加入购物车',
					message: message,
					type: 'success',
					position: 'top-left',
				})
				// if (confirm(message + "是否前往购物车?")) {
				// 	router.push({
				// 		name: "userOrder",
				// 	});
				// } else {
				// 	router.push({
				// 		name: "book",
				// 	});
				// }
			})
			.catch((Error) => {
				console.log(Error);
			});
	}
};
</script>

<style scoped>
.info_div {
	margin-bottom: 8%;
}

.info_span {
	width: 30%;
	margin-right: 15%;
}
</style>