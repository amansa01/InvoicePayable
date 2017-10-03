import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {LoginComponent} from "./login/login.component";
import {MainpageComponent} from "./mainpage/mainpage.component";
import {AdminComponent} from "./Admin/admin.component";
import {CreateVendor} from "./vendor/createvendor/create.vendor";
import {HomeComponent} from "./home/home.component";
import {VendorMainpageComponent} from "./vendor/vendor.mainpage/vendor.mainpage.component";
import {CreatebillComponent} from "./vendor/createbills/createbill.component";
import {PayinvoiceComponent} from "./Admin/payinvoice/payinvoice.component";
import {VendorInvoiceComponent} from "./vendor/vendorInvoice/vendorInvoice.component";
import {VendorsummaryComponent} from "./vendor/summaryInvoice/vendorsummary.component";
import {VendorlistComponent} from "./Admin/vendorList/vendorlist.component";
import {AuthGuard} from "./authguard";
import {SearchInvoiceComponent} from "./vendor/searchInvoice/searchInvoice.component";
import {AdminInvoiceComponent} from "./Admin/adminInvoice/adminInvoice.component";
import {AdminSearchInvoiceComponent} from "./Admin/searchInvoice/adminSearchInvoice.component";

const appRoutes: Routes = [
  {path: '', redirectTo: 'main', pathMatch: 'full'},
  // {
  //   path: 'login',
  //   component: LoginComponent
//  },
  {
    path: 'main',
    component: MainpageComponent,
    children: [
      {path: '', component: HomeComponent}
    ]
  },
  {
    path: 'admin',
    component: AdminComponent,
    children: [
      {path: '', component: HomeComponent},
      {path: 'create', component: CreateVendor,canActivate: [AuthGuard]},
      {path: 'payinvoice', component: PayinvoiceComponent,canActivate: [AuthGuard]},
      {path: 'invoices', component: AdminInvoiceComponent,canActivate: [AuthGuard]},
      {path: 'vendorlist', component: VendorlistComponent,canActivate: [AuthGuard]},
      {path: 'summary', component: VendorsummaryComponent,canActivate: [AuthGuard]},
      {path: 'search', component: AdminSearchInvoiceComponent,canActivate: [AuthGuard]}
    ]
  }, {
    path: 'vendor', component: VendorMainpageComponent,
    children: [
      {path: '', component: HomeComponent},
      {path: 'uploadbills', component: CreatebillComponent,canActivate: [AuthGuard]},
      {path: 'invoices', component: VendorInvoiceComponent,canActivate: [AuthGuard]},
      {path: 'summary', component: VendorsummaryComponent,canActivate: [AuthGuard]},
      {path: 'search', component: SearchInvoiceComponent,canActivate: [AuthGuard]}
    ]
  },
];
export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes);
