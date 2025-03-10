import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { DeviceDataService } from '../services/device-data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone:false
})

export class HomePage implements OnInit {
  
  private map!: L.Map;
  // This property will be bound to the input in the template
  deviceId: number | null = null;

  constructor(private deviceDataService: DeviceDataService) {}

  ngOnInit(): void {
    delete (L.Icon.Default.prototype as any)._getIconUrl;
    L.Icon.Default.mergeOptions({
      iconRetinaUrl: 'assets/marker-icon-2x.png',
      iconUrl: 'assets/marker-icon.png',
      shadowUrl: 'assets/marker-shadow.png',
    });
    // Initialize the map on load
    this.initMap();
  }

  private initMap(): void {
    // Center the map on London with zoom 13
    this.map = L.map('map').setView([51.505, -0.09], 13);
  
    // Add an OSM tile layer
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    }).addTo(this.map);
  
    // Add a marker
    const marker = L.marker([51.5, -0.09]).addTo(this.map);
    marker.bindPopup('I am a standalone popup.').openPopup();
  
    // Add a circle
    const circle = L.circle([51.508, -0.11], {
      color: 'red',
      fillColor: '#f03',
      fillOpacity: 0.5,
      radius: 500
    }).addTo(this.map);
    circle.bindPopup('I am a circle.');
  
    // Add a polygon
    const polygon = L.polygon([
      [51.509, -0.08],
      [51.503, -0.06],
      [51.51, -0.047]
    ]).addTo(this.map);
    polygon.bindPopup('I am a polygon.');
  }
  ionViewDidEnter() {
    setTimeout(() => {
      this.map.invalidateSize();
    }, 0);
  }

  // Called when the user clicks "Load Data"
  onLoadDeviceData(): void {
    if (this.deviceId) {
      this.loadMovementData(this.deviceId);
    }
  }

  private loadMovementData(id_device: number): void {
    // Fetch data from the service
    this.deviceDataService.getMovementByIdDevice(id_device).subscribe((data) => {
      const coordinates: L.LatLngTuple[] = data.map((point: any) => [
        point.latitude,
        point.longitude
      ]);

      // Draw a polyline for all the coordinates
      const polyline = L.polyline(coordinates, { color: 'blue' }).addTo(this.map);

      // Re-center and re-zoom the map to fit the new route
      this.map.fitBounds(polyline.getBounds());

      // Marker animation
      this.animateMarker(coordinates);
    });
  }

  private animateMarker(coordinates: L.LatLngTuple[]): void {
    // Place the marker at the first coordinate
    const marker = L.marker(coordinates[0]).addTo(this.map);
    let index = 0;

    const interval = setInterval(() => {
      if (index >= coordinates.length - 1) {
        clearInterval(interval);
        return;
      }
      index++;

      // Move the marker to the new coordinate
      marker.setLatLng(coordinates[index]);

      // Optional: show a popup on each step
      marker
        .bindPopup(
          `Point ${index + 1}: Latitude ${coordinates[index][0]}, Longitude ${
            coordinates[index][1]
          }`
        )
        .openPopup();
    }, 1000);
  }
}