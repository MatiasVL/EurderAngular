import {Component, OnInit} from '@angular/core';
import {Item} from "../item";
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {ItemService} from "../item.service";
import {ItemsComponent} from "../items/items.component";

@Component({
  selector: 'app-item-create',
  templateUrl: './item-create.component.html',
  styleUrls: ['./item-create.component.css']
})
export class ItemCreateComponent implements OnInit {


  constructor(
    private route: ActivatedRoute,
    private itemService: ItemService,
    private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  createItem(name, description, price, amount): void {
    this.itemService.createItem({name}, {description}, {price}, {amount})
      .subscribe(item => {
        this.items.push(item);
      });
  }

}
