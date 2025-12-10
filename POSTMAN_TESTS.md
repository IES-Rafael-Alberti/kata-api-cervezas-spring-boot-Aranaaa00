# PRUEBAS DE LA API CON POSTMAN

**Base URL:** `http://localhost:8080`

---

## 🍺 ENDPOINT `/beer` - CRUD COMPLETO

### 1️⃣ CREATE - Crear Cerveza
```
POST http://localhost:8080/beer
Content-Type: application/json

{
    "cerveceriaId": 1,
    "nombre": "IPA Artesanal",
    "categoriaId": 1,
    "estiloId": 5,
    "graduacionAlcoholica": 6.5,
    "ibu": 45.0,
    "srm": 12.5,
    "upc": 123456789,
    "rutaArchivo": "/images/ipa.jpg",
    "descripcion": "Cerveza IPA con aroma cítrico y amargor equilibrado",
    "usuarioAgregador": 1
}
```
**Resultado esperado:** `201 Created` con los datos de la cerveza creada

---

### 2️⃣ READ - Obtener Todas las Cervezas
```
GET http://localhost:8080/beer
```
**Resultado esperado:** `200 OK` con array de todas las cervezas

---

### 3️⃣ READ - Obtener Cerveza por ID
```
GET http://localhost:8080/beer/1
```
**Resultado esperado:** `200 OK` con datos de la cerveza o `404 Not Found`

---

### 4️⃣ READ - Buscar Cervezas por Nombre
```
GET http://localhost:8080/beer/buscar?nombre=IPA
```
**Resultado esperado:** `200 OK` con array de cervezas que contengan "IPA"

---

### 5️⃣ READ - Filtrar Cervezas por Estilo
```
GET http://localhost:8080/beer/filtrar/estilo/5
```
**Resultado esperado:** `200 OK` con array de cervezas del estilo especificado

---

### 6️⃣ READ - Filtrar Cervezas por Cervecería
```
GET http://localhost:8080/beer/filtrar/cerveceria/1
```
**Resultado esperado:** `200 OK` con array de cervezas de la cervecería especificada

---

### 7️⃣ UPDATE - Actualizar Cerveza Completa (PUT)
```
PUT http://localhost:8080/beer/1
Content-Type: application/json

{
    "cerveceriaId": 1,
    "nombre": "IPA Artesanal Premium",
    "categoriaId": 1,
    "estiloId": 5,
    "graduacionAlcoholica": 7.0,
    "ibu": 50.0,
    "srm": 13.0,
    "upc": 123456789,
    "rutaArchivo": "/images/ipa-premium.jpg",
    "descripcion": "Versión premium de nuestra IPA con más cuerpo",
    "usuarioAgregador": 1
}
```
**Resultado esperado:** `200 OK` con datos actualizados o `404 Not Found`

---

### 8️⃣ UPDATE - Actualizar Cerveza Parcial (PATCH)
```
PATCH http://localhost:8080/beer/1
Content-Type: application/json

{
    "nombre": "IPA Artesanal Edición Especial",
    "graduacionAlcoholica": 7.5,
    "ibu": 55.0
}
```
**Resultado esperado:** `200 OK` con datos actualizados o `404 Not Found`

---

### 9️⃣ DELETE - Eliminar Cerveza
```
DELETE http://localhost:8080/beer/1
```
**Resultado esperado:** `204 No Content` o `404 Not Found`

---

## 🏭 ENDPOINT `/breweries` - SOLO LECTURA

### 🔟 Obtener Todas las Cervecerías
```
GET http://localhost:8080/breweries
```
**Resultado esperado:** `200 OK` con array de cervecerías

---

### 1️⃣1️⃣ Obtener Cervecería por ID
```
GET http://localhost:8080/breweries/1
```
**Resultado esperado:** `200 OK` con datos de la cervecería o `404 Not Found`

---

## 📂 ENDPOINT `/categories` - SOLO LECTURA

### 1️⃣2️⃣ Obtener Todas las Categorías
```
GET http://localhost:8080/categories
```
**Resultado esperado:** `200 OK` con array de categorías

---

### 1️⃣3️⃣ Obtener Categoría por ID
```
GET http://localhost:8080/categories/1
```
**Resultado esperado:** `200 OK` con datos de la categoría o `404 Not Found`

---

## 🎨 ENDPOINT `/styles` - SOLO LECTURA

### 1️⃣4️⃣ Obtener Todos los Estilos
```
GET http://localhost:8080/styles
```
**Resultado esperado:** `200 OK` con array de estilos

---

### 1️⃣5️⃣ Obtener Estilo por ID
```
GET http://localhost:8080/styles/1
```
**Resultado esperado:** `200 OK` con datos del estilo o `404 Not Found`

---

## 📝 INSTRUCCIONES PARA GUARDAR RESULTADOS

1. Abre Postman
2. Crea una nueva colección llamada "Kata API Cervezas"
3. Para cada petición:
   - Crea una nueva request
   - Copia el método (GET/POST/PUT/PATCH/DELETE) y la URL
   - Si es POST/PUT/PATCH, añade el body JSON en la pestaña "Body" → "raw" → "JSON"
   - Ejecuta la petición
   - Guarda el resultado haciendo clic en "Save Response" → "Save to file"
   
4. Para exportar toda la colección:
   - Haz clic derecho en la colección "Kata API Cervezas"
   - Selecciona "Export"
   - Elige "Collection v2.1"
   - Guarda el archivo JSON

5. Nombra los archivos de resultados como:
   - `01_create_beer_result.json`
   - `02_get_all_beers_result.json`
   - `03_get_beer_by_id_result.json`
   - etc.

---

## ⚠️ NOTAS IMPORTANTES

- Asegúrate de que la aplicación Spring Boot esté ejecutándose en `localhost:8080`
- La base de datos debe estar poblada con datos iniciales de los scripts SQL
- Verifica que Docker esté corriendo si usas la BD en contenedor
- Los IDs en los ejemplos (1, 5, etc.) deben existir en tu base de datos
- El campo `ultimaModificacion` se genera automáticamente en el servidor

---

**Total: 15 endpoints probados** ✅
