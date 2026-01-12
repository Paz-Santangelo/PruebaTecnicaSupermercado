# 🔧 Solución: Railway no detecta tu repositorio

## 🎯 Tu Repositorio
**URL**: https://github.com/Paz-Santangelo/PruebaTecnicaSupermercado.git

## ⚠️ PROBLEMAS IDENTIFICADOS

### Problema 1: Permisos de GitHub en Railway
Railway necesita acceso explícito a tu repositorio de GitHub.

### Problema 2: Estructura del repositorio
Tu repositorio tiene esta estructura:
```
PruebaTecnicaSupermercado/  (repo raíz)
├── docker-compose.yml
└── PruebaTecSupermercado/  (carpeta del proyecto)
    ├── Dockerfile
    ├── pom.xml
    ├── src/
    └── target/
```

Railway busca el Dockerfile en la raíz, pero está dentro de `PruebaTecSupermercado/`.

---

## ✅ SOLUCIÓN PASO A PASO

### **Opción 1: Configurar Railway correctamente (RECOMENDADA)**

#### Paso 1: Verifica permisos de GitHub en Railway
1. Ve a Railway Dashboard: https://railway.app/dashboard
2. Click en tu perfil (esquina superior derecha)
3. **Settings** → **GitHub**
4. Click en **"Configure GitHub App"**
5. Verifica que el repositorio `PruebaTecnicaSupermercado` esté en la lista
6. Si no está, click en **"Select repositories"** y agrega `PruebaTecnicaSupermercado`
7. Click **"Save"**

#### Paso 2: Crear nuevo proyecto en Railway
1. Ve a Railway: https://railway.app/new
2. Click en **"Deploy from GitHub repo"**
3. Busca: `Paz-Santangelo/PruebaTecnicaSupermercado`
4. Si ahora aparece, selecciónalo ✅

#### Paso 3: Configurar Root Directory
⚠️ **IMPORTANTE**: Railway debe saber que el Dockerfile está dentro de la carpeta `PruebaTecSupermercado/`

1. Después de seleccionar el repo, en la configuración del servicio:
2. Ve a **Settings** → **Build**
3. En **"Root Directory"** escribe: `PruebaTecSupermercado`
4. Railway ahora buscará el Dockerfile en esa carpeta ✅

#### Paso 4: Agregar MySQL
1. En tu proyecto de Railway, click en **"New"** → **"Database"** → **"Add MySQL"**
2. Railway creará automáticamente las variables de entorno:
   - `MYSQL_URL`
   - `MYSQL_HOST`
   - `MYSQL_PORT`
   - `MYSQL_USER`
   - `MYSQL_PASSWORD`
   - `MYSQL_DATABASE`

#### Paso 5: Configurar variables de entorno
1. Ve a tu servicio (el de Spring Boot)
2. Click en **"Variables"**
3. Agrega estas variables:
   ```
   DB_URL = ${{MySQL.MYSQL_URL}}
   DB_USER_NAME = ${{MySQL.MYSQL_USER}}
   DB_PASSWORD = ${{MySQL.MYSQL_PASSWORD}}
   ```
4. Railway vinculará automáticamente con la base de datos ✅

#### Paso 6: Deploy
1. Railway desplegará automáticamente
2. Espera 2-3 minutos
3. ¡Listo! 🎉

---

### **Opción 2: Reorganizar la estructura del repositorio**

Si la Opción 1 no funciona, reorganiza tu repo:

#### Paso 1: Mover archivos a la raíz
```powershell
# Desde: C:\Users\Usuario\Desktop\Paz\Programacion\Pruebas Tecnicas\PruebaTecnica-Supermercado

# Mover Dockerfile a la raíz
Move-Item -Path "PruebaTecSupermercado\Dockerfile" -Destination "."

# Mover pom.xml a la raíz
Move-Item -Path "PruebaTecSupermercado\pom.xml" -Destination "."

# Mover carpeta src a la raíz
Move-Item -Path "PruebaTecSupermercado\src" -Destination "."
```

#### Paso 2: Actualizar docker-compose.yml
Cambiar:
```yaml
build: PruebaTecSupermercado
```

Por:
```yaml
build: .
```

#### Paso 3: Commit y push
```powershell
git add .
git commit -m "Reorganizar estructura para Railway"
git push origin main
```

#### Paso 4: Intentar nuevamente en Railway
Ahora Railway debería detectar el Dockerfile en la raíz ✅

---

### **Opción 3: Usar CLI de Railway (Alternativa)**

Si las opciones anteriores fallan, usa la CLI:

#### Paso 1: Instalar Railway CLI
```powershell
iwr https://railway.app/install.ps1 | iex
```

#### Paso 2: Iniciar sesión
```powershell
railway login
```

#### Paso 3: Ir a la carpeta del proyecto
```powershell
cd "C:\Users\Usuario\Desktop\Paz\Programacion\Pruebas Tecnicas\PruebaTecnica-Supermercado\PruebaTecSupermercado"
```

#### Paso 4: Inicializar proyecto
```powershell
railway init
```

#### Paso 5: Vincular con tu proyecto en Railway
```powershell
railway link
```

#### Paso 6: Agregar MySQL
```powershell
railway add --plugin mysql
```

#### Paso 7: Deploy
```powershell
railway up
```

---

## 🔍 VERIFICAR QUE RAILWAY DETECTÓ EL DOCKERFILE

Después de configurar, verifica:
1. En el Dashboard de Railway
2. Ve a tu servicio
3. Click en **"Deployments"**
4. Deberías ver: **"Building with Dockerfile"** ✅

---

## ❓ SI AÚN NO FUNCIONA

### Verifica estos puntos:
- [ ] ✅ El repositorio es **público** en GitHub
- [ ] ✅ Railway tiene **permisos de acceso** al repo
- [ ] ✅ El **Dockerfile** existe en la carpeta correcta
- [ ] ✅ Configuraste el **Root Directory** en Railway
- [ ] ✅ El JAR existe en `target/` (compilaste con `mvn clean package`)

---

## 🎯 MI RECOMENDACIÓN

1. **Prueba primero la Opción 1** (configurar Root Directory)
2. Si no funciona, usa la **Opción 3** (CLI de Railway)
3. La Opción 2 (reorganizar) es más trabajo, déjala como último recurso

---

## 📞 SIGUIENTE PASO

Dime cuál de estas opciones probaste y qué mensaje de error (si hay alguno) te muestra Railway.
También puedes compartir una captura del dashboard de Railway para ayudarte mejor 🤝

