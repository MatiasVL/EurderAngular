import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ItemsComponent} from "./items/items.component";
import {ItemDetailComponent} from "./item-detail/item-detail.component";
import {ItemCreateComponent} from "./item-create/item-create.component";
import {ItemUpdateComponent} from "./item-update/item-update.component";


const routes: Routes = [
  {path: 'items', component: ItemsComponent},
  {path: 'detail/:id', component: ItemDetailComponent},
  {path: 'create', component: ItemCreateComponent },
  {path: 'edit',component: ItemUpdateComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
