import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';

import {AppComponent} from './app.component';
import {AngularFireModule, AuthProviders, AuthMethods} from 'angularfire2';
import {LoginComponent} from "../login/login.component";

import {routing} from "../app.routing";
import {AdminComponent} from "../Admin/admin.component";
import {CreateVendor} from "../vendor/createvendor/create.vendor";
import {HomeComponent} from "../home/home.component";
import {CreateVendorService} from "../vendor/vendor.service";
import {MainpageComponent} from "../mainpage/mainpage.component";
import {VendorMainpageComponent} from "../vendor/vendor.mainpage/vendor.mainpage.component";
import {ConstantComponent} from "../constant.component";
import {CreatebillComponent} from "../vendor/createbills/createbill.component";
import {UserDetailsService} from "../userdetail.service";
import {PayinvoiceComponent} from "../Admin/payinvoice/payinvoice.component";
import {VendorInvoiceComponent} from "../vendor/vendorInvoice/vendorInvoice.component";
import {VendorsummaryComponent} from "../vendor/summaryInvoice/vendorsummary.component";
import {VendorlistComponent} from "../Admin/vendorList/vendorlist.component";
import {LoginService} from "../login/login.service";
import {AuthGuard} from "../authguard";
// import {OrderByPipe} from "../vendor/dataFilterPipe";
import {NG2DataTableModule} from "angular2-datatable-pagination";

import {SearchInvoiceComponent} from "../vendor/searchInvoice/searchInvoice.component";
import {AdminInvoiceComponent} from "../Admin/adminInvoice/adminInvoice.component";
import {VendorModule} from "../vendor/vendor.module";
import {AdminSearchInvoiceComponent} from "../Admin/searchInvoice/adminSearchInvoice.component";

export const firebaseconfig = {
  apiKey: "AIzaSyBmr4m7hQ4cN_m9vAM9u8eRg8eVC9LXE6k",
  authDomain: "loginhome-96a0d.firebaseapp.com",
  databaseURL: "https://loginhome-96a0d.firebaseio.com", projectId: "loginhome-96a0d",
  storageBucket: "",
  messagingSenderId: "614322103945"
};

@NgModule({
  declarations: [AppComponent, LoginComponent, MainpageComponent, AdminComponent,HomeComponent, PayinvoiceComponent, SearchInvoiceComponent,AdminInvoiceComponent,AdminSearchInvoiceComponent
  ],
  imports: [
    BrowserModule, NG2DataTableModule,VendorModule,
    FormsModule, routing,
    HttpModule, FormsModule, ReactiveFormsModule,
    AngularFireModule.initializeApp(firebaseconfig, {
      provider: AuthProviders.Twitter,
      method: AuthMethods.Redirect
    }),
  ],
  providers: [ConstantComponent, UserDetailsService,LoginService,AuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule {
}
