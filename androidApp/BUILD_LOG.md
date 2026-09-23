# Build Log - Mobile To-Do App

## Prompt / Request
Inicialização do projeto no Android Studio e definição da arquitetura base para o Mobile To-Do App em Kotlin Multiplatform.

## Decision Summary
- **UI:** Compose Multiplatform.
- **Persistência:** SQLDelight.
- **Navegação:** Voyager.
- **Arquitetura:** MVVM.
- **IDE:** Android Studio escolhido por já possuir o ecossistema Android (SDK e Emuladores) integrado.

## Actions Performed
- Projeto aberto e sincronizado no Android Studio.
- Arquivo BUILD_LOG.md criado e primeiro commit realizado.

## Result
Projeto base configurado com sucesso e Gradle sincronizado sem erros.

## Current Status
Completed



## Prompt / Request
Tentativa de rodar o projeto em um dispositivo físico falhou com erro de resolução de dependência: "Could not find com.android.tools.build:aapt2".

## Problems / Errors
O Gradle tentou baixar o `aapt2` apenas no repositório `mavenCentral`, falhando ao não encontrar as ferramentas oficiais de build do Android. O repositório do Google estava ausente nas configurações.

## Fixes Attempted
- Adicionada a declaração `google()` nos blocos `pluginManagement.repositories` e `dependencyResolutionManagement.repositories` do arquivo `settings.gradle.kts`.

## Result
Build configurado para buscar dependências do Android no repositório correto. Aguardando resultado da compilação.

## Current Status
Completed (Fix applied)


## Prompt / Request
Erro de compilação: "Build was configured to prefer settings repositories over project repositories".

## Problems / Errors
O arquivo `build.gradle.kts` principal estava declarando repositórios (MavenRepo), o que entrava em conflito com a configuração `FAIL_ON_PROJECT_REPOS` definida no `settings.gradle.kts`.

## Fixes Attempted
- Bloco `repositories { ... }` removido do arquivo `build.gradle.kts` raiz, centralizando a resolução de dependências no `settings.gradle.kts`.

## Result
Conflito de repositórios resolvido.

## Current Status
Completed


## Prompt / Request
Requisitado o uso do Java 21 em vez de realizar o downgrade para o Java 11 na compilação do Android.

## Problems / Errors
A tentativa anterior de igualar as versões (Inconsistent JVM targets) reduziu a capacidade do projeto para o Java 11, impedindo o uso de recursos modernos da linguagem.

## Fixes Attempted
- Alterado o `jvmTarget` de `JvmTarget.JVM_11` para `JvmTarget.JVM_21` no ficheiro `androidApp/build.gradle.kts`.
- Mantida a configuração `jvmToolchain(21)`.

## Result
Projeto configurado com sucesso para utilizar Java 21 em ambas as tarefas de compilação.

## Current Status
Completed