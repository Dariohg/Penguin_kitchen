#!/bin/bash

# Ruta para almacenar usuarios y configuraciones
USERS_FILE="$HOME/.ssh/github/github_users.txt"
SSH_CONFIG="$HOME/.ssh/config"
SSH_KEYS_DIR="$HOME/.ssh/github"

# Asegurarse de que el directorio existe
mkdir -p "$SSH_KEYS_DIR"

# Cargar usuarios existentes desde el archivo
declare -A USERS
declare -A HOSTS
if [[ -f $USERS_FILE ]]; then
    while IFS=, read -r username email; do
        USERS["$username"]="$email"
        HOSTS["$username"]="github-$username"
    done < "$USERS_FILE"
fi

# Función para registrar un nuevo usuario
registrar_usuario() {
    read -p "Ingrese el nombre de usuario: " username
    read -p "Ingrese el correo del usuario: " email

    # Agregar al archivo de usuarios
    echo "$username,$email" >> "$USERS_FILE"

    # Generar claves SSH
    SSH_KEY_PATH="$SSH_KEYS_DIR/${username}_key"
    ssh-keygen -t ed25519 -C "$email" -f "$SSH_KEY_PATH" -N ""

    # Agregar configuración al archivo ~/.ssh/config
    {
        echo "# Configuración para la cuenta $username"
        echo "Host github-$username"
        echo "  HostName github.com"
        echo "  User git"
        echo "  IdentityFile $SSH_KEY_PATH"
        echo ""
    } >> "$SSH_CONFIG"

    # Mostrar clave pública
    echo "Claves SSH creadas:"
    echo "  Clave pública: $SSH_KEY_PATH.pub"
    echo "  Clave privada: $SSH_KEY_PATH"
    echo "Contenido de la clave pública (regístrala en GitHub):"
    cat "$SSH_KEY_PATH.pub"

    # Actualizar arreglos locales
    USERS["$username"]="$email"
    HOSTS["$username"]="github-$username"
}

# Función para configurar el host remoto del repositorio
configurar_repositorio() {
    read -p "Ingrese el usuario del repositorio: " usuario_repo
    read -p "Ingrese el nombre del repositorio (e.g., repo.git): " nombre_repo
    git remote set-url origin "git@github-$usuario_repo:$usuario_repo/$nombre_repo"
    echo "URL remota configurada para el repositorio:"
    git remote -v
}

# Mostrar menú
while true; do
    echo "Seleccione una opción:"
    echo "1) Cambiar de cuenta Git"
    echo "2) Registrar un nuevo usuario y clave SSH"
    echo "3) Configurar el host del repositorio"
    echo "4) Salir"

    read -p "Opción: " opcion

    case $opcion in
        1)
            # Mostrar opciones con índices numéricos
            echo "Selecciona la cuenta de Git/GitHub a usar:"
            keys=("${!USERS[@]}") # Guardar las claves en un array
            for i in "${!keys[@]}"; do
                key="${keys[$i]}"
                echo "  $i) $key (${USERS[$key]})"
            done

            # Leer selección numérica
            read -p "Ingresa el número de la cuenta: " seleccion

            # Validar selección
            if [[ -z "${keys[$seleccion]}" ]]; then
                echo "Error: selección no válida."
                continue
            fi

            cuenta="${keys[$seleccion]}"

            # Configurar usuario y correo para Git
            git config --global user.name "$cuenta"
            git config --global user.email "${USERS[$cuenta]}"
            echo "Configuración de Git actualizada: ${USERS[$cuenta]}"

            # Cambiar clave SSH si aplica
            if [[ -n "${HOSTS[$cuenta]}" ]]; then
                echo "Configurando conexión SSH para '${HOSTS[$cuenta]}'..."
                git remote set-url origin "git@${HOSTS[$cuenta]}:usuario/repo.git"
                echo "URL del remoto actualizada para usar '${HOSTS[$cuenta]}'."
            fi

            # Verificar configuración actual
            echo "Usuario actual:"
            git config user.name
            git config user.email
            echo "URL remota actual:"
            git remote -v

            # Probar conexión SSH
            echo "Probar conexión SSH..."
            ssh -T "git@${HOSTS[$cuenta]}" || {
                echo "Error: Fallo en la conexión SSH con '${HOSTS[$cuenta]}'. Verifica tu configuración."
                exit 1
            }
            ;;
        2)
            registrar_usuario
            ;;
        3)
            configurar_repositorio
            ;;
        4)
            echo "Saliendo..."
            break
            ;;
        *)
            echo "Opción no válida."
            ;;
    esac
done

