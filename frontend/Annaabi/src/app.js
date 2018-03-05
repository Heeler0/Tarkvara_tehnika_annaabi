import {HttpClient, json} from 'aurelia-fetch-client';

export class app {
  data1;

  getJson () {
    let client = new HttpClient();
    client.fetch("http://194.135.95.77:8080/api/getFileList")
      .then(response => response.json())
      .then(data => {
        this.data1 = data
      });
  }

  convertToString(text) {
    return JSON.stringify(text);
  }

  convertDate(number) {
    var date = new Date(number * 1000);
    var day = date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();
    return day + '.' + monthIndex + '.' + year;
  }

  getDate(obj) {
    var number = obj.uploadDate;
    return number;
  }

  getFileName(obj) {
    var header = obj.fileName;
    return header
  }

  getDesc(obj) {
    var desc = obj.fileDescription;
    return desc;
  }

  getSize(obj) {
    var size = obj.fileSize;
    return size
  }

  download(obj) {
    var url = "http://194.135.95.77:8080/api/getFile/";
    url += obj.fileName;
    console.log(url);
    return url;
  }

  doUpload() {
    //console.log("Works");
    return this.upload('api/upload', {}, this.selectedFiles[0]).then(() => this.clearFiles());
  }

  upload(url, data, files, method = "POST") {
    let formData = new FormData();

    for (let i = 0; i < files.length; i++) {
      formData.append(`files[${i}]`, files[i]);
    }

    return this.http.fetch(url, {
      method: method,
      body: formData,
      headers: new Headers()
    }).then(response => response.json());
  }
  constructor () {

    this.http = new HttpClient();
    this.http.configure(config => { config
      .useStandardConfiguration()
      .withBaseUrl(`api/upload`); // hetkene väljund oleks http://localhost:9000/api/uploadapi/upload
    });
  }
}
