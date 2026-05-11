# 📖 Tutorial Completo: Configuração do JARVIS A56

Este guia detalha passo a passo como configurar o **JARVIS A56** no seu Samsung Galaxy A56 com Android 16 e One UI 8.0.

## 🎯 Objetivos

Ao final deste tutorial, você terá:
- ✅ Shizuku 14+ pareado via ADB sem fio
- ✅ Device Owner configurado (opcional - Protocolo Extremis)
- ✅ JARVIS A56 compilado e instalado
- ✅ Todos os serviços ativados e funcionando

---

## 📱 Parte 1: Preparar o Samsung Galaxy A56

### Passo 1.1: Ativar Opções de Desenvolvedor

1. Abra **Configurações**
2. Vá para **Sobre o telefone**
3. Procure por **Número da compilação**
4. **Toque 7 vezes** em **Número da compilação**
5. Você verá a mensagem: *"Você é um desenvolvedor!"*
6. Volte para **Configurações** (a opção **Opções de desenvolvedor** agora aparecerá)

### Passo 1.2: Ativar Depuração USB

1. Vá para **Configurações > Opções de desenvolvedor**
2. Procure por **Depuração USB**
3. **Ative** a opção
4. Uma caixa de diálogo aparecerá perguntando se deseja permitir depuração USB
5. Toque em **Permitir**

### Passo 1.3: Ativar Depuração sem Fio (ADB)

1. Em **Opções de desenvolvedor**, procure por **Depuração sem fio**
2. **Ative** a opção
3. O sistema exibirá uma mensagem com **IP e porta** (ex: `192.168.1.100:5555`)
4. **Anote esses valores** - você precisará deles em breve

---

## 💻 Parte 2: Configurar ADB no Computador

### Passo 2.1: Instalar Android SDK Platform Tools

#### No Windows:
1. Baixe em: https://developer.android.com/tools/releases/platform-tools
2. Extraia em um local seguro (ex: `C:\android-sdk-tools`)
3. Adicione ao PATH do Windows

#### No macOS/Linux:
```bash
# macOS
brew install android-platform-tools

# Linux (Ubuntu/Debian)
sudo apt-get install android-tools-adb
```

### Passo 2.2: Conectar via ADB sem Fio

```bash
# Substitua 192.168.1.100:5555 pelo IP:porta do seu dispositivo
adb connect 192.168.1.100:5555

# Verificar conexão
adb devices

# Saída esperada:
# List of attached devices
# 192.168.1.100:5555       device
```

---

## 🔧 Parte 3: Instalar e Configurar Shizuku

### Passo 3.1: Baixar Shizuku 14+

1. Acesse: https://github.com/RikkaApps/Shizuku/releases
2. Baixe a versão mais recente do APK (ex: `Shizuku-14.1.0.apk`)
3. Transfira para o Samsung Galaxy A56 via:
   - USB (mais rápido)
   - Email
   - Google Drive
   - ADB: `adb push Shizuku-14.1.0.apk /sdcard/Download/`

### Passo 3.2: Instalar Shizuku no Dispositivo

1. Abra o **Gerenciador de Arquivos**
2. Navegue até **Download**
3. Toque em **Shizuku-14.1.0.apk**
4. Toque em **Instalar**
5. Aguarde a conclusão

### Passo 3.3: Parear Shizuku via ADB

No seu computador, execute:

```bash
# Conectar ao dispositivo
adb connect 192.168.1.100:5555

# Conceder permissão ao Shizuku para acessar configurações
adb shell pm grant dev.rikka.shizuku android.permission.WRITE_SECURE_SETTINGS

# Conceder permissão para mudar configurações
adb shell pm grant dev.rikka.shizuku android.permission.CHANGE_CONFIGURATION

# Conceder permissão para usar serviços
adb shell pm grant dev.rikka.shizuku android.permission.QUERY_ALL_PACKAGES

# Iniciar Shizuku
adb shell am start -n dev.rikka.shizuku/.MainActivity
```

### Passo 3.4: Verificar Pareamento no Dispositivo

1. Abra o app **Shizuku** no seu Galaxy A56
2. Você deve ver a mensagem: *"Conectado"*
3. Se não conectar, tente:
   - Reiniciar o app Shizuku
   - Reconectar via ADB: `adb connect 192.168.1.100:5555`

---

## 🛡️ Parte 4: Configurar Device Owner (Protocolo Extremis)

### ⚠️ Aviso Importante
Ativar Device Owner dará ao JARVIS A56 controle total do dispositivo. **Use com cuidado!**

### Passo 4.1: Verificar Device Owner Atual

```bash
adb shell dpm get-device-owner
```

Se retornar nada, você pode prosseguir. Se retornar um app, remova primeiro:

```bash
adb shell dpm clear-device-owner-user 0
```

### Passo 4.2: Ativar Device Owner para JARVIS A56

```bash
# Conectar ao dispositivo
adb connect 192.168.1.100:5555

# Ativar Device Owner
adb shell dpm set-device-owner com.jarvis.a56/.receiver.JarvisDeviceAdmin

# Verificar se foi bem-sucedido
adb shell dpm get-device-owner

# Saída esperada:
# com.jarvis.a56/.receiver.JarvisDeviceAdmin
```

### Passo 4.3: Ativar Protocolo Extremis

1. Abra o app **JARVIS A56**
2. Toque no **Reator Arc** (círculo central)
3. Diga: *"Protocolo extremis"*
4. O app confirmará: *"Protocolo Extremis ativado"*

---

## 🚀 Parte 5: Compilar e Instalar JARVIS A56

### Passo 5.1: Clonar o Repositório

```bash
git clone <repository-url>
cd JARVIS_A56_Android
```

### Passo 5.2: Abrir no Android Studio

1. Abra **Android Studio**
2. Selecione **File > Open**
3. Navegue até `JARVIS_A56_Android`
4. Clique em **OK**
5. Aguarde o Gradle sincronizar

### Passo 5.3: Compilar APK

**Opção 1: Via Android Studio**
1. Clique em **Build > Build Bundle(s) / APK(s) > Build APK(s)**
2. Aguarde a compilação (pode levar 5-10 minutos)
3. O APK será salvo em `app/build/outputs/apk/debug/app-debug.apk`

**Opção 2: Via Terminal**
```bash
./gradlew build
# ou
./gradlew assembleDebug
```

### Passo 5.4: Instalar no Dispositivo

```bash
# Conectar ao dispositivo
adb connect 192.168.1.100:5555

# Instalar APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Ou via Android Studio: Run > Run 'app'
```

---

## ⚙️ Parte 6: Ativar Serviços Necessários

### Passo 6.1: Ativar Serviço de Acessibilidade

1. Abra **Configurações**
2. Vá para **Acessibilidade**
3. Procure por **Serviços de Acessibilidade**
4. Toque em **JARVIS A56**
5. **Ative** o serviço
6. Toque em **Permitir** quando solicitado

### Passo 6.2: Ativar Listener de Notificações

1. Abra **Configurações**
2. Vá para **Notificações**
3. Procure por **Acesso às notificações**
4. Toque em **JARVIS A56**
5. **Ative** o acesso

### Passo 6.3: Ativar Permissões de Câmera e Microfone

1. Abra **Configurações**
2. Vá para **Apps > JARVIS A56 > Permissões**
3. Ative:
   - **Câmera**
   - **Microfone**
   - **Localização**
   - **Contatos**
   - **Calendário**
   - **SMS**

### Passo 6.4: Permitir Overlay (Bola Flutuante)

1. Abra **Configurações**
2. Vá para **Apps > JARVIS A56 > Permissões avançadas**
3. Procure por **Exibir sobre outros apps**
4. **Ative** a permissão

---

## 🎤 Parte 7: Testar Comandos de Voz

### Ativar Wake Word

1. Abra o app **JARVIS A56**
2. Diga: **"Fala JARVIS"**
3. O app deve responder: *"Às suas ordens, Senhor"*

### Testar Comandos Básicos

```
"Ligar wifi"              → Ativa WiFi
"Desligar bluetooth"      → Desativa Bluetooth
"Qual é a bateria"        → Retorna nível de bateria
"Ligar lanterna"          → Ativa lanterna
"Abrir câmera"            → Abre câmera frontal
"Tirar foto"              → Captura foto 50MP
"Gravar vídeo"            → Grava vídeo 4K60
"Bloquear tela"           → Bloqueia a tela
"Protocolo fantasma"      → Ativa modo silencioso por 1h
```

---

## 🐛 Troubleshooting

### Problema: Shizuku não conecta

**Solução:**
```bash
# Reconectar via ADB
adb disconnect
adb connect 192.168.1.100:5555

# Reiniciar Shizuku no dispositivo
adb shell am force-stop dev.rikka.shizuku
adb shell am start -n dev.rikka.shizuku/.MainActivity
```

### Problema: Permissões não funcionam

**Solução:**
1. Vá para **Configurações > Apps > JARVIS A56**
2. Toque em **Permissões**
3. Ative manualmente todas as permissões
4. Reinicie o app JARVIS A56

### Problema: Device Owner não ativa

**Solução:**
```bash
# Limpar Device Owner anterior
adb shell dpm clear-device-owner-user 0

# Tentar novamente
adb shell dpm set-device-owner com.jarvis.a56/.receiver.JarvisDeviceAdmin
```

### Problema: APK não instala

**Solução:**
```bash
# Desinstalar versão anterior
adb uninstall com.jarvis.a56

# Instalar novamente
adb install app/build/outputs/apk/debug/app-debug.apk
```

---

## 📊 Verificação Final

Após completar todos os passos, verifique:

- [ ] Shizuku 14+ instalado e pareado
- [ ] Device Owner ativado (se desejado)
- [ ] JARVIS A56 compilado e instalado
- [ ] Serviço de Acessibilidade ativado
- [ ] Listener de Notificações ativado
- [ ] Todas as permissões concedidas
- [ ] Wake word "Fala JARVIS" funciona
- [ ] Comandos de voz respondem corretamente

---

## 🔗 Referências Úteis

- **Shizuku GitHub:** https://github.com/RikkaApps/Shizuku
- **Samsung Knox SDK:** https://www.samsungknox.com/
- **Android Developer:** https://developer.android.com/
- **Kotlin Documentation:** https://kotlinlang.org/docs/
- **Jetpack Compose:** https://developer.android.com/jetpack/compose

---

## 📞 Suporte

Se encontrar problemas:

1. Verifique o **logcat** no Android Studio:
   ```bash
   adb logcat | grep JARVIS
   ```

2. Reinicie o dispositivo

3. Reinstale o app

4. Consulte o arquivo `README.md` para mais informações

---

**Parabéns!** 🎉 Você agora tem o **JARVIS A56** totalmente configurado e pronto para usar.

*"Às suas ordens, Senhor."*
