const total = document.getElementById("total");
const returned = document.getElementById("returned");
const not_returned = document.getElementById("not_returned");
const totexpecting_returnal = document.getElementById("expecting_return");

const user_status = document.getElementById("user_status");

function list() {
  $.ajax({
    url: "data.json",
    type: "GET",
    dataType: "json",
    success: function (data) {
      console.log(data.userData.totalUserInfo[0].status);

      user_status.innerHTML = `
      <tr>
            <td>대출도서</td>
            <td>반납도서</td>
            <td>미반납도서</td>
            <td>반납예정도서</td>
            <td>대출가능권수</td>
            <td>이용상태</td>
            <td>대출정지기간</td>
          </tr>
          <tr>
        <td>${data.userData.totalBookList.length}</td>
        <td>${data.userData.totalReturnList.length}</td>
        <td>${data.userData.overdueBookList.length}</td>
        <td>${data.userData.soonReturnList.length}</td>
        <td>${
          data.userData.totalUserInfo[0].maxBook -
          data.userData.soonReturnList.length
        }</td>
        <td>${
          data.userData.totalUserInfo[0].status === "00"
            ? "사용가능"
            : "사용불가"
        }</td>
        <td>${
          data.userData.totalUserInfo.serviceStop == null
            ? "-"
            : data.userData.totalUserInfo.serviceStop
        }</td>
    </tr>`;

      total.innerHTML = `<tr>
      <td>도서번호</td>
      <td>도서명</td>
      <td>저자</td>
      <td>대출일자</td>
      <td>반납일자</td>
    </tr>`;
      for (let i = 0; i < data.userData.totalBookList.length; i++) {
        total.innerHTML += `<tr>
          <td>${data.userData.totalBookList[i].book_seq}</td>
          <td>${data.userData.totalBookList[i].book_title}</td>
          <td>${data.userData.totalBookList[i].book_author}</td>
          <td>${data.userData.totalBookList[i].borrow_start}</td>
          <td>${data.userData.totalBookList[i].borrow_end}</td>
      </tr>`;
      }

      returned.innerHTML = `<tr>
      <td>도서번호</td>
      <td>도서명</td>
      <td>저자</td>
      <td>대출일자</td>
      <td>반납일자</td>
    </tr>`;
      for (let i = 0; i < data.userData.totalReturnList.length; i++) {
        returned.innerHTML += `<tr>
            <td>${data.userData.totalReturnList[i].book_seq}</td>
            <td>${data.userData.totalReturnList[i].book_title}</td>
            <td>${data.userData.totalReturnList[i].book_author}</td>
            <td>${data.userData.totalReturnList[i].borrow_start}</td>
            <td>${data.userData.totalReturnList[i].borrow_end}</td>
        </tr>`;
      }

      // 미반납도서
      not_returned.innerHTML = `
      <tr>
            <td>도서번호</td>
            <td>도서명</td>
            <td>저자</td>
            <td>대출일자</td>
            <td>반납일자</td>
          </tr>`;
      for (let i = 0; i < data.userData.overdueBookList.length; i++) {
        not_returned.innerHTML += `<tr>
              <td>${data.userData.overdueBookList[i].book_seq}</td>
              <td>${data.userData.overdueBookList[i].book_title}</td>
              <td>${data.userData.overdueBookList[i].book_author}</td>
              <td>${data.userData.overdueBookList[i].borrow_start}</td>
              <td>${data.userData.overdueBookList[i].borrow_end}</td>
          </tr>`;
      }
      totexpecting_returnal.innerHTML = `<tr>
      <td>도서번호</td>
      <td>도서명</td>
      <td>저자</td>
      <td>대출일자</td>
      <td>반납일자</td>
    </tr>`;
      for (let i = 0; i < data.userData.soonReturnList.length; i++) {
        totexpecting_returnal.innerHTML += `<tr>
              <td>${data.userData.soonReturnList[i].book_seq}</td>
              <td>${data.userData.soonReturnList[i].book_title}</td>
              <td>${data.userData.soonReturnList[i].book_author}</td>
              <td>${data.userData.soonReturnList[i].borrow_start}</td>
              <td>${data.userData.soonReturnList[i].borrow_end}</td>
          </tr>`;
      }
    },
  });
}
