from min_path_cost import MinPathGUI
import sys
import tkinter.messagebox as mes


def main():
    size = 4
    if len(sys.argv) == 2:
        size = int(sys.argv[1])

    gui = MinPathGUI(size)


main()
