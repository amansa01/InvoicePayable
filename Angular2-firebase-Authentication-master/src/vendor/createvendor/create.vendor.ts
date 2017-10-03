import {Component, ElementRef, style, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Http} from '@angular/http';
import {CreateVendorService} from '../vendor.service';
import {VendorModel} from './vendor.model';

@Component({
  selector: 'create-vendor',
  templateUrl: './create.vendor.html',
  styleUrls: ['./create.vendor.css']
})
export class CreateVendor {
  form: FormGroup;
  checkbox: VendorModel;
  checkboxlist: VendorModel [] = [];
  loading = false;
  items: any;
  creatingVendor: any;
  items1: any;
  arrAll: CreateVendor[];


  @ViewChild('fileInput') fileInput: ElementRef;
  selected: any;

  constructor(private fb: FormBuilder, private http: Http, private createvendorservise: CreateVendorService) {
    this.createForm();
  }


  createForm() {
    this.form = this.fb.group({
      // name: ['', Validators.required],
      avatar: ['', Validators.required]
    });
  }

  onFileChange(event) {
    if (event.target.files.length > 0) {
      let file = event.target.files[0];
      this.form.get('avatar').setValue(file);
      console.log('avatar');
      console.log(this.form.get('avatar').setValue(file));
    }
  }

  private prepareSave(): any {
    let input = new FormData();
    // input.append('name', this.form.get('name').value);
    input.append('file', this.form.get('avatar').value);
    return input;
  }

  onSubmit() {
    const formModel = this.prepareSave();
    this.loading = true;
    console.log('before');

    console.log(formModel);
    console.log('after');
    this.createvendorservise.checkVendor(formModel).then((Response) => {
      this.items = Response.json();
      // alert(Response.text());
    }).catch((error) => {
      console.log(error);
    });
    setTimeout(() => {
      // alert('done!');
      this.loading = false;
    }, 1000);
  }

  clearFile() {
    this.form.get('avatar').setValue(null);
    this.fileInput.nativeElement.value = '';
  }

  save() {
    this.items1 = this.items;
  }


  //
  // updateChecked2(value,event){
  //   if(event.target.checked){
  //     this.checkboxlist.push(this.items1[value]);
  //   }
  //   else if (!event.target.checked){
  //     let indexx = this.items1.indexOf(this.items1[value]);
  //     this.checkboxlist.splice(indexx,1);
  //   }
  //   console.log(this.checkboxlist)
  // }


  updateChecked2(index: number) {

    this.checkboxlist.push(this.items[index]);
  }

  createVendor() {
    console.log('In createVendor', this.checkboxlist)
    this.creatingVendor = this.checkboxlist;
    console.log(this.creatingVendor);
    this.createvendorservise.createVendor(this.creatingVendor).then((Response) => {
console.log(Response.json())
      alert(Response.toString());
    }).catch((error) => {
      console.log(error);
    });
    this.creatingVendor=null;
  }


  //
  // updateChecked2(index, event) {
  //   if (event.checked) {
  //     console.log(this.checkboxlist.push(this.items[index]));
  //   }
  //   else if (!event.checked) {
  //    console.log( this.checkboxlist.indexOf(this.items[index]));
  //   }
  //
  //
  //   console.log(this.checkboxlist);
  //
  // }


}
