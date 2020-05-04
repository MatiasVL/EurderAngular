import { Component, OnInit } from '@angular/core';
import {Item} from "../item";

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {
  item: Item = {
    name: 'Item 1',
    price: 5,
    stockUrgency: 'urgent'
  };

  constructor() { }

  ngOnInit(): void {
  }

}
