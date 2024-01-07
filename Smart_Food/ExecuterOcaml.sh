#!/bin/bash
# echo -e "\033[38;5;226m Execution d'Ocaml...\033[0m"
# echo -e "\033[38;5;226m Ajout au PATH...\033[0m"
PATH="$PATH:/home/ugolinux/.opam/default/bin"
cd ocaml/
# echo -e "\033[38;5;226m Make clean...\033[0m"
# make clean > /dev/null
eval $(opam env)
# ocaml --version
# opam --version
# opam show csv
# echo -e "\033[38;5;226m Compilation...\033[0m"
# make > /dev/null
# echo -e "\033[38;5;226m Execution du main...\033[0m"
if [ "$1" = "DISPLAY" ]; then {
    # echo "mode : DISPLAY"
    ./main.exe DISPLAY
}
elif [ "$1" = "QUANTITY" ]; then {
    ./main.exe QUANTITY $2
}
elif  [ "$1" = "LAST_DAY" ]; then {
    # echo "mode : LAST_DAY"
    ./main.exe LAST_DAY
}
elif  [ "$1" = "LAST_MONTH" ]; then {
    # echo "mode : LAST_MONTH"
    ./main.exe LAST_MONTH
}
elif  [ "$1" = "LAST_YEAR" ]; then {
    # echo "mode : LAST_MONTH"
    ./main.exe LAST_YEAR
}
else
    echo "Argument invalide"
fi

