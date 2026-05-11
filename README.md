# JARVIS A56 - Assistente Pessoal Android Nativo

**JARVIS A56** é um assistente pessoal avançado para Samsung Galaxy A56 (Android 16, One UI 8.0, Knox 3.12) desenvolvido em **Kotlin + Jetpack Compose**, com integração máxima ao sistema operacional via **Shizuku** e **Samsung Knox SDK**.

## 📋 Requisitos

- **Android Studio** 2023.3 ou superior
- **Kotlin** 1.9.22+
- **JDK 17+**
- **Android SDK 36** (compileSdk)
- **minSdk 34** (Android 14)
- **Samsung Galaxy A56** com Android 16 e One UI 8.0

## 🚀 Recursos Principais

### Módulo Sistema
- ✅ Controle de WiFi, Bluetooth, GPS, NFC, Hotspot
- ✅ Controle de brilho, volume, taxa de refresh
- ✅ Câmera frontal/traseira (50MP, 4K60)
- ✅ Monitoramento de bateria, temperatura, ciclos
- ✅ Bloqueio de tela, screenshots, gravação de tela
- ✅ Integração Samsung Knox SDK
- ✅ Alarmes, timers, eventos de calendário
- ✅ Gerenciamento de apps (instalar, desinstalar, limpar dados)

### Módulo JARVIS
- ✅ Leitura/resposta de WhatsApp, Telegram, SMS, Gmail
- ✅ Rotinas One UI via Bixby Intent
- ✅ Geofencing por GPS
- ✅ Resumo proativo (7h: clima, agenda, bateria, trânsito)
- ✅ Segurança pessoal (3 erros = foto + localização)
- ✅ Localização de dispositivo (alarme + flash)
- ✅ Modo Fantasma (1h desconectado)

### Interface
- ✅ Overlay flutuante com animação Reator Arc
- ✅ HUD Lock na tela de bloqueio One UI 8
- ✅ TTS Samsung (voz masculina PT-BR)
- ✅ Registro de Missões (últimas 24h)

## 🔧 Configuração Inicial

### 1. Clonar o Repositório

```bash
git clone <repository-url>
cd JARVIS_A56_Android
```

### 2. Abrir no Android Studio

1. Abra **Android Studio**
2. Selecione **File > Open**
3. Navegue até a pasta `JARVIS_A56_Android`
4. Clique em **OK**

### 3. Configurar Shizuku (ADB sem Fio)

#### No Samsung Galaxy A56:

1. **Ativar Opções de Desenvolvedor:**
   - Vá para **Configurações > Sobre o telefone**
   - Toque 7 vezes em **Número da compilação**
   - Volte para **Configurações > Opções de desenvolvedor**

2. **Ativar Depuração USB:**
   - Em **Opções de desenvolvedor**, ative **Depuração USB**

3. **Ativar Depuração sem Fio:**
   - Em **Opções de desenvolvedor**, ative **Depuração sem fio (ADB)**
   - Anote o IP e porta exibidos (ex: `192.168.1.100:5555`)

4. **Instalar Shizuku:**
   - Baixe o APK de Shizuku 14+ em [GitHub Rikka](https://github.com/RikkaApps/Shizuku/releases)
   - Instale no dispositivo
   - Abra Shizuku e siga as instruções para pareamento via ADB

#### No Computador (Terminal):

```bash
# Conectar ao dispositivo via ADB sem fio
adb connect 192.168.1.100:5555

# Verificar conexão
adb devices

# Conceder permissões Shizuku
adb shell pm grant dev.rikka.shizuku android.permission.WRITE_SECURE_SETTINGS
adb shell pm grant dev.rikka.shizuku android.permission.CHANGE_CONFIGURATION
```

### 4. Ativar Device Owner (Opcional - Protocolo Extremis)

Para ativar o **Protocolo Extremis** (controle total com root), é necessário configurar Device Owner:

```bash
# No computador, com o dispositivo conectado via ADB
adb shell dpm set-device-owner com.jarvis.a56/.receiver.JarvisDeviceAdmin
```

### 5. Compilar e Executar

```bash
# No Android Studio, clique em "Run" ou use:
./gradlew build
./gradlew installDebug
```

## 📱 Uso

### Ativar Serviço de Acessibilidade

1. Vá para **Configurações > Acessibilidade > Serviços de Acessibilidade**
2. Ative **JARVIS A56**
3. Conceda as permissões solicitadas

### Ativar Listener de Notificações

1. Vá para **Configurações > Notificações > Acesso às notificações**
2. Ative **JARVIS A56**

### Usar Comandos de Voz

- **"Fala JARVIS"** - Ativa o assistente
- **"Ligar wifi"** - Ativa WiFi
- **"Desligar bluetooth"** - Desativa Bluetooth
- **"Ligar lanterna"** - Ativa lanterna
- **"Qual é a bateria"** - Retorna nível de bateria
- **"Protocolo extremis"** - Ativa modo com controle total (requer root)
- **"Protocolo fantasma"** - Desativa acessos por 1 hora

Veja `comandos.json` para a lista completa de 100+ comandos.

## 📂 Estrutura do Projeto

```
JARVIS_A56_Android/
├── app/
│   ├── src/main/
│   │   ├── java/com/jarvis/a56/
│   │   │   ├── MainActivity.kt           # Interface principal Compose
│   │   │   ├── SystemControlManager.kt   # Controle de hardware
│   │   │   ├── ShizukuManager.kt         # Gerenciador ADB
│   │   │   ├── KnoxManager.kt            # Integração Knox SDK
│   │   │   ├── viewmodel/
│   │   │   │   └── JarvisViewModel.kt    # ViewModel Compose
│   │   │   ├── service/
│   │   │   │   ├── JarvisAccessibilityService.kt
│   │   │   │   ├── JarvisNotificationListener.kt
│   │   │   │   ├── JarvisVoiceService.kt
│   │   │   │   └── FloatingWidgetService.kt
│   │   │   ├── receiver/
│   │   │   │   └── JarvisDeviceAdmin.kt
│   │   │   └── ui/theme/
│   │   │       ├── JarvisTheme.kt
│   │   │       └── Type.kt
│   │   ├── res/
│   │   │   ├── values/
│   │   │   │   ├── strings.xml
│   │   │   │   ├── colors.xml
│   │   │   │   └── themes.xml
│   │   │   └── xml/
│   │   │       ├── accessibility_service_config.xml
│   │   │       └── device_admin_receiver.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
├── comandos.json                         # 100+ comandos pré-configurados
└── README.md
```

## 🔐 Permissões Necessárias

O projeto requer **47 permissões críticas**:

- `WRITE_SECURE_SETTINGS` - Modificar configurações do sistema
- `BIND_ACCESSIBILITY_SERVICE` - Serviço de acessibilidade
- `QUERY_ALL_PACKAGES` - Listar todos os apps
- `SYSTEM_ALERT_WINDOW` - Overlay flutuante
- `BIND_DEVICE_ADMIN` - Controle de dispositivo
- `BIND_NOTIFICATION_LISTENER_SERVICE` - Ouvir notificações
- `BIND_VOICE_INTERACTION` - Serviço de voz
- E muitas outras...

Veja `AndroidManifest.xml` para a lista completa.

## 🛡️ Segurança

### Modo Fantasma
```kotlin
// Desativa todos os acessos por 1 hora
jarvisViewModel.activateGhostMode()
```

### Foto de Segurança
```kotlin
// Após 3 erros de senha, captura foto frontal + localização
jarvisViewModel.triggerSecurityPhoto()
```

### Protocolo Extremis (Requer Root)
```kotlin
// Ativa controle total do sistema
knoxManager.activateExtremisProtocol()
```

## 🐛 Troubleshooting

### Shizuku não conecta
- Verifique se Shizuku 14+ está instalado
- Reconecte via ADB: `adb connect 192.168.1.100:5555`
- Reinicie Shizuku no dispositivo

### Permissões não funcionam
- Verifique se o serviço de acessibilidade está ativado
- Conceda permissões manualmente em **Configurações > Apps > JARVIS A56**

### Device Owner não ativa
- Certifique-se de que nenhum outro app é Device Owner
- Use: `adb shell dpm clear-device-owner-user 0`

## 📚 Referências

- [Shizuku GitHub](https://github.com/RikkaApps/Shizuku)
- [Samsung Knox SDK](https://www.samsungknox.com/)
- [Android Accessibility Service](https://developer.android.com/guide/topics/ui/accessibility/service)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Android 16 API Reference](https://developer.android.com/reference)

## 📝 Licença

Este projeto é fornecido como está. Uso pessoal apenas.

---

**Desenvolvido por:** Manus AI  
**Data:** Maio 2026  
**Versão:** 1.0.0  
**Status:** ⚠️ Protocolo Extremis Ativo
