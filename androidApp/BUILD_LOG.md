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


## Prompt / Request
Implementar a navegação básica da aplicação conforme a estratégia de implementação (Passo 3).

## Decision Summary
- **Biblioteca selecionada:** Voyager (`cafe.adriel.voyager`).
- **Motivo:** É nativa para Compose Multiplatform, suporta injeção de dependências facilmente, possui gestão de estado com `ScreenModel` (ideal para a nossa arquitetura MVVM) e simplifica transições de ecrãs sem depender do Android Jetpack Navigation.

## Actions Performed
- Adicionadas as referências de versão e módulos do Voyager no ficheiro `gradle/libs.versions.toml` (navigator, screenmodel e transitions).
- Aplicadas as dependências no bloco `commonMain` do ficheiro `shared/build.gradle.kts`.
- Gradle sincronizado com sucesso.

## Result
Projeto pronto para receber os primeiros ecrãs e a estrutura de navegação.

## Current Status
Completed

## Prompt / Request
Criar a navegação básica e a estrutura dos 3 ecrãs exigidos pela especificação.

## Decision Summary
- **Estratégia de Navegação:** `Navigator` do Voyager a iniciar com `TaskListScreen`.
- **Passagem de dados (Ecrã 1 -> Ecrã 2):** Decidido passar apenas o `taskId: Long?` (ID da tarefa) como parâmetro para o `TaskDetailScreen`. Se for nulo, a aplicação sabe que é o modo de criação; se for preenchido, é o modo de edição. Esta abordagem evita problemas de serialização pesada ao não passar objetos inteiros entre ecrãs.
- **Animações:** Utilizado `SlideTransition` para dar uma sensação mais nativa e fluida à mudança de ecrãs.

## Actions Performed
- Ficheiro `Screens.kt` criado no módulo `commonMain` contendo as três implementações da interface `Screen` do Voyager.
- `App.kt` refatorado para remover o código *boilerplate* e implementar o `Navigator` com o tema Material.

## Result
Navegação entre a Lista de Tarefas, Detalhe da Tarefa e Gestão de Categorias implementada com sucesso.

## Current Status
Completed