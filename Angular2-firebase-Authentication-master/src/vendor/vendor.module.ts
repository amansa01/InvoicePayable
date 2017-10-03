import {CreateVendorService} from "./vendor.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpModule} from "@angular/http";
import {BrowserModule} from "@angular/platform-browser";
import {routing} from "../app.routing";
import {ConstantComponent} from "../constant.component";
import {UserDetailsService} from "../userdetail.service";
import {CreateVendor} from "./createvendor/create.vendor";
import {VendorMainpageComponent} from "./vendor.mainpage/vendor.mainpage.component";
import {CreatebillComponent} from "./createbills/createbill.component";
import {VendorInvoiceComponent} from "./vendorInvoice/vendorInvoice.component";
import {VendorsummaryComponent} from "./summaryInvoice/vendorsummary.component";
import {VendorlistComponent} from "../Admin/vendorList/vendorlist.component";
import {NgModule} from "@angular/core";
@NgModule({
  declarations: [CreateVendor, VendorMainpageComponent, CreatebillComponent,VendorInvoiceComponent,VendorsummaryComponent,VendorlistComponent
  ],
  imports: [
    BrowserModule,
    FormsModule, routing,
    HttpModule, FormsModule, ReactiveFormsModule,
  ],
  providers: [CreateVendorService],

})
export class VendorModule {
}
