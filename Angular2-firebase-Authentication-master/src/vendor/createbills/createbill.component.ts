import {Component, ElementRef, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

import {Http} from '@angular/http';

import {CreateVendor} from '../createvendor/create.vendor';
import {CreateVendorService} from '../vendor.service';
import {UserDetailsService} from '../../userdetail.service';
import {CreatebillInterface} from './createbill.interface';
import {UploadbillComponent} from "./uploadbill.component";

@Component({
  selector: 'create-bill',
  templateUrl: './createbill.component.html',
  styleUrls: ['./createbill.component.css']
})
export class CreatebillComponent {
  form: FormGroup;
  items: any;
  found: boolean;
  totalPrice: number;
  loading = false;
  email: string;
  invoicedate: Date;
  duedate: Date;

  createbill_email: any;
  createbill_name: any;
  createbill_amount: any;
  createbill_invoicedate:any;
  createbill_duedate: any;

  @ViewChild('fileInput') fileInput: ElementRef;

  constructor(private fb: FormBuilder, private bills: CreateVendorService, private userDetailsService: UserDetailsService) {
    this.createForm();
  }

  createForm() {
    this.form = this.fb.group({

      avatar: null,
      duedate: ['', Validators.required],
      invoicedate: ['', Validators.required]
    });
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      this.form.get('avatar').setValue(file);
    }
  }

  onSubmit() {
    let formModel: FormData = new FormData();
    this.loading = true;
    formModel.append('email', this.email);
    formModel.append('invoicedate', this.form.value.invoicedate.toLocaleString());
    formModel.append('duedate', this.form.value.duedate.toLocaleString());
    this.invoicedate = this.form.value.invoicedate.toLocaleString();
    this.duedate = this.form.value.duedate.toLocaleString();
    console.log('Date String', this.form.value.duedate.toLocaleString());
    formModel.append('file', this.form.value.avatar);
    this.bills.uploadBills(formModel).then((Response) => {
      this.items = Response.json();
      console.log(Response.json());
    }).catch((error) => {
      console.log(error);
    });

    setTimeout(() => {
      this.loading = false;
    }, 1000);
  }

  uploadbill() {
    this.found = true;
    for (let i = 0; i < this.items.length; i++) {

      if (this.items[i].product === 'Total') {
        this.totalPrice = this.items[i].totalPrice;
        console.log('product :' + this.items[i].product);
        if (this.items[i].status === 'false') {
          this.found = false;
        }
      }
    }
    console.log('found ' + this.found);
    console.log('Not of' + (!(this.found)));



    if (!this.found || this.invoicedate>this.duedate) {
      alert('Some Problem in the Bill or Dates. Please Check it.');
    } else {
      this.createbill_email = this.userDetailsService.user.auth.providerData[0].email;
      this.createbill_name = this.userDetailsService.user.auth.providerData[0].displayName;
      this.createbill_amount = this.totalPrice;
      this.createbill_invoicedate = this.invoicedate;
      this.createbill_duedate = this.duedate;
      this.bills.uploadbill(this.createbill_email, this.createbill_name, this.createbill_amount,
        this.createbill_invoicedate, this.createbill_duedate)
        .then((Response) => {

        this.items = Response.json();
        console.log(Response.json());
          alert('Bills Uploaded Sussesfully');
      }).catch((error) => {
        console.log(error);
      });
    }
  }

  clearFile() {
    this.form.get('avatar').setValue(null);
    this.fileInput.nativeElement.value = '';
  }

}
