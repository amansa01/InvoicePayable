import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {CreateVendorService} from "../vendor.service";
import {UserDetailsService} from "../../userdetail.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {PaymentmodelInterface} from "../paymentmodel.interface";
import * as webdriver from "selenium-webdriver";
// import {OrderByPipe} from '../dataFilterPipe';
import {NG2DataTableModule} from 'angular2-datatable-pagination';
import * as _ from "lodash";

@Component({
  selector: 'vendor-invoice',
  templateUrl: './searchInvoice.component.html',
  styleUrls:['./searchInvoice.component.css']

})
export class SearchInvoiceComponent implements OnInit {
  isValid: any;
  public data: any[];
  public filterQuery = "";
  public rowsOnPage = 10;
  public activePage = 1;
  public sortBy: any;
  public sortOrder = "asc";
  public itemsTotal;
  items: any;
  email: string;
  test: any;
  isDesc: any;
  sortElement:any
  invoice: string;
  amount: string;
  start: any;
  end: any;
  private searchForm: FormGroup;

  search = {
    "text": '',
    "invoiceDate": '', "email": '',
    "dueDate":''
  }
  pageList: any;
  totalpages;
  sortList:any;
  pages;
  emailtopay: string;
  paginationArrayForInvoice;
  public paymentDetails = this._fb.group({
    accountName: new FormControl(),
    accountNumber: new FormControl(),
    verifyAccountNumber: new FormControl(),
    ifsc: new FormControl(),
    accountType: new FormControl()
  });

  constructor(private _fb: FormBuilder, private router: Router, private createvendorservise: CreateVendorService, private userDetailsService: UserDetailsService) {

  }

  ngOnInit() {
    console.log('In NgonIt');

    // this.invoicePagination();
    this.email = this.userDetailsService.user.auth.providerData[0].email;
    this.test = {'email': this.email};
    // this.loadData();
    this.searchForm = this._fb.group({
      text: [null, Validators.required],
      invoiceDate:[''],
      dueDate:['']
    });

    // this.createvendorservise.searchList(this.email,this.search.text,this.search.invoiceDate,this.search.dueDate, 0, 3).then((response) => {
    //   this.pageList = response.json();
    //   console.log('loadInvoiceList', response);
    // }).catch((error) => {
    //   console.log(error);
    // });
  }

  onSearch() {

    this.search.text = this.searchForm.value.text;
    this.search.invoiceDate = this.searchForm.value.invoiceDate;
    this.search.dueDate = this.searchForm.value.dueDate;
    this.search.email = this.userDetailsService.user.auth.providerData[0].email;
    this.invoicePagination();
    this.createvendorservise.searchList(this.email,this.search.text,this.search.invoiceDate,this.search.dueDate, 0, 3).then((response) => {
        this.pageList = response.json();
        console.log('In search', response);
      }).catch((error) => {
        console.log(error);
      });
  }

  invoicePagination() {
    this.search.text = this.searchForm.value.text;
    this.search.email = this.userDetailsService.user.auth.providerData[0].email;
    this.createvendorservise.getCountofSearchInvoice(this.search).then((Response) => {
      console.log('In search Method', Response.json());
      this.totalpages = Response.json();
      this.PaginationArrayCallBackFunction();
    }).catch((error) => {
      console.log(error);
    });
  }

  PaginationArrayCallBackFunction() {

    this.paginationArrayForInvoice = this.createvendorservise.getPaginationArray(this.totalpages);
    console.log('Total No Of Pages We Got In search ', this.paginationArrayForInvoice)
  }


  loadInvoiceList(pageid) {
    this.email = this.userDetailsService.user.auth.providerData[0].email;
    this.start = ( pageid - 1) * 3;
    this.end = pageid * 3;
    this.createvendorservise.searchList(this.email,this.search.text,this.search.invoiceDate,this.search.dueDate, this.start, this.end).then((response) => {
      this.pageList = response.json();
      console.log('loadInvoiceList In search', response);
    }).catch((error) => {
      console.log(error);
    });
  }


  public onPay(value: PaymentmodelInterface) {
    this.invoice = value.invoice;
    this.amount = value.amount;
    this.emailtopay = value.email;
  }


  public makePayment(value: PaymentmodelInterface) {
    value.invoice = this.invoice;
    value.email = this.emailtopay;
    value.amount = this.amount;
    if (value.accountNumber != value.verifyAccountNumber) {
      alert('Please Enter Correct Account Number');
      document.getElementById('dismissModal').click();
    }
    else {
      this.createvendorservise.payToVendor(value).then((Response) => {
        // this.data = Response.json();

        console.log('Received Payment' ,Response.toString())
        alert('Payment Sussessfull');
        document.getElementById('dismissModal').click();
      }).catch((error) => {
        console.log(error);
      });
    }
    console.log('payment Values', value);
  }


}
