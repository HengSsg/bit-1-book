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
        <td>${data.userData.rentNum}</td>
        <td>${data.userData.returnNum}</td>
        <td>${data.userData.noReturnNum}</td>
        <td>${data.userData.soonReturnNum}</td>
        <td>${data.userData.availRentNum}</td>
        <td>${data.userData.status}</td>
        <td>${data.userData.stopPeriod}</td>
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
