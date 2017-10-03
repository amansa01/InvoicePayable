import {Http} from '@angular/http';
import {Injectable} from '@angular/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import {Headers} from '@angular/http';
import {ConstantComponent} from "../constant.component";
import {UserDetailsService} from "../userdetail.service";
import {CreatebillInterface} from "./createbills/createbill.interface";


@Injectable()
export class CreateVendorService {
  emailll:any
  session:any;
  total:any;
  constructor(private http: Http, private url: ConstantComponent,private userDetailsService:UserDetailsService ) {
  }

  checkVendor(myForm: any) {
    event.preventDefault();
    // var headers = new Headers();
    // headers.append('Content-Type', 'multipart/form-data ');
    return this.http.post(this.url.checkVendorList, myForm, {withCredentials: true}).toPromise();

  }

  createVendor(myform: any) {
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url.createVendorList, myform, {headers,withCredentials: true}).toPromise();

  }

  uploadBills(myform: any) {
    event.preventDefault();
    var email = this.userDetailsService.user.auth.providerData[0].email
    // var headers = new Headers();
    // headers.append('Content-Type', 'application/json');
    // headers.append('Accept-Encoding', 'multipart/form-data ');

    console.log('MyForm invoice', myform.invoice);
    console.log('myform_email: ', email);
    return this.http.post(this.url.uploadBills, myform, {withCredentials: true}).toPromise();

  }

  uploadbill(email: any, name: any, amount: any, invoicedate: any, duedate: any) {
    event.preventDefault();
    const headers = new Headers();
    headers.append('Content-Type', 'application/json');
    const createBill = {
      'email': email,
      'name': name,
      'amount': amount,
      'invoiceDate': invoicedate,
      'dueDate': duedate
    }
    return this.http.post(this.url.uploadBill, createBill, {headers,withCredentials: true}).toPromise();

  }

  vendorInvoice(myform) {
    // event.preventDefault();
    console.log(myform);

    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url.vendorInvoice, myform, {headers,withCredentials: true});
  }

  vendorSummary(myform) {
    // event.preventDefault();
    console.log(myform);

    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url.vendorSummary, myform, {headers,withCredentials: true}).toPromise();
  }


  payToVendor(myform) {
    event.preventDefault();
    console.log(myform);

    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url.payment, myform, {headers,withCredentials: true}).toPromise();
  }



  getCountofTotalInvoice(){
    event.preventDefault();
this.emailll=this.userDetailsService.user.auth.providerData[0].email;
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    this.total = this.http.post(this.url.totalInvoice,this.emailll,{ withCredentials: true}).toPromise();
    return this.total;
  }
  getCountofTotalVendors(){
    event.preventDefault();
    this.emailll=this.userDetailsService.user.auth.providerData[0].email;
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    this.total = this.http.post(this.url.totalVendor,this.emailll,{ withCredentials: true}).toPromise();
    return this.total;
  }


  getPaginationArray(Count) {

    const paginationArray = [];
    let x: number;
    x = parseInt(Count, 10);
    const val = 3
    let noOfPages: number = parseInt((x / 3 ) + '', 10);
    if (x % val) {
      noOfPages = noOfPages + 1;
    }
    while (paginationArray.length < noOfPages) {
      paginationArray.push( paginationArray.length + 1 );
    }
    return paginationArray;

  }
  pageList(email : any,start:any,end :any ){
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.url.pagelist+'?email='+email+'&start='+start+'&end='+end,{ withCredentials: true}).toPromise();
  }

  vendorList(email:any,start:any,end:any) {
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.get(this.url.vendorList+'?email='+email+'&start='+start+'&end='+end, {headers,withCredentials: true}).toPromise();
  }

  searchList(email : any,text:any,invoiceDate:any,dueDate:any,start:any,end :any){
    event.preventDefault();
   const search={
      'email':email,
       'text':text,
      'invoiceDate':invoiceDate,
        'dueDate':dueDate
    }
    var headers = new Headers();
    console.log('in Search list email is ', email)
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url.search+'?start='+start+'&end='+end ,search,{ withCredentials: true}).toPromise();


  }
  // sortedList(email : any,start:any,end :any ){
  //   event.preventDefault();
  //   var headers = new Headers();
  //   headers.append('Content-Type', 'application/json');
  //   return this.http.get(this.url.sortList+'?email='+email+'&start='+start+'&end='+end,{ withCredentials: true}).toPromise();
  // }

  onVendorSearch(value:any){
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    return this.http.post(this.url.vendorSearch,value,{ withCredentials: true}).toPromise();

  }


  getCountofSearchInvoice(search:any){
    event.preventDefault();
    var headers = new Headers();
    headers.append('Content-Type', 'application/json');
    this.total = this.http.post(this.url.searchTotal,search,{ withCredentials: true}).toPromise();
    return this.total;
  }

}
