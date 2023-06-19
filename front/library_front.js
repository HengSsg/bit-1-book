const total = document.getElementById("total");
const returned = document.getElementById("returned");
const not_returned = document.getElementById("not_returned");
const totexpecting_returnal = document.getElementById("expecting_return");

$.ajax({
  url: "data.json",
  type: "GET",
  dataType: "json",
  success: function (data) {
    for (let i = 0; i < data.totalBookList.length; i++) {
      total.innerHTML += `<tr>
          <td>${data.totalBookList[i].bookNum}</td>
          <td>${data.totalBookList[i].bookName}</td>
          <td>${data.totalBookList[i].author}</td>
          <td>${data.totalBookList[i].rentDate}</td>
          <td>${data.totalBookList[i].returnDate}}</td>
      </tr>`;
    }

    for (let i = 0; i < data.totalReturnList.length; i++) {
      returned.innerHTML += `<tr>
            <td>${data.totalReturnList[i].bookNum}</td>
            <td>${data.totalReturnList[i].bookName}</td>
            <td>${data.totalReturnList[i].author}</td>
            <td>${data.totalReturnList[i].rentDate}</td>
            <td>${data.totalReturnList[i].returnDate}}</td>
        </tr>`;
    }
    for (let i = 0; i < data.noReturnList.length; i++) {
      not_returned.innerHTML += `<tr>
              <td>${data.noReturnList[i].bookNum}</td>
              <td>${data.noReturnList[i].bookName}</td>
              <td>${data.noReturnList[i].author}</td>
              <td>${data.noReturnList[i].rentDate}</td>
              <td>${data.noReturnList[i].returnDate}}</td>
          </tr>`;
    }
    for (let i = 0; i < data.soonReturnList.length; i++) {
      totexpecting_returnal.innerHTML += `<tr>
              <td>${data.soonReturnList[i].bookNum}</td>
              <td>${data.soonReturnList[i].bookName}</td>
              <td>${data.soonReturnList[i].author}</td>
              <td>${data.soonReturnList[i].rentDate}</td>
              <td>${data.soonReturnList[i].returnDate}}</td>
          </tr>`;
    }
  },
});

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
    },
  });
}
