import tkinter
import tkinter.messagebox
import random


class MinPathGUI():
    def __init__(self, size):
        self.size = size if size < 20 else 20
        self.frame_width = 50
        self.max_value = 20

        win_size = str(self.size * self.frame_width)
        win_size = win_size + 'x' + win_size
        self.window = tkinter.Tk()
        self.window.title("Minimum Cost Path Solver")

        self.grid_frame = tkinter.Frame(self.window)
        self.buttons_frame_width = 100
        self.buttons_frame = tkinter.Frame(self.window,
                                           width=self.buttons_frame_width,
                                           padx=10)

        self.button_width = 15

        # user's inputs
        self.input_label = tkinter.Label(self.buttons_frame,
                                         text='Guess the minimum sum',
                                         wraplength=self.buttons_frame_width)
        self.input = tkinter.Entry(self.buttons_frame, width=self.button_width)
        self.check_button = tkinter.Button(self.buttons_frame,
                                           text='Check Answer',
                                           command=self.check_answer,
                                           width=self.button_width,
                                           padx=6,
                                           pady=3)

        # show path button
        self.show_button = tkinter.Button(self.buttons_frame,
                                          text='Show Path',
                                          command=self.show_path,
                                          width=self.button_width,
                                          padx=6,
                                          pady=3)
        # create ne values for grid
        self.new_grid_button = tkinter.Button(self.buttons_frame,
                                              text='New Grid',
                                              command=self.create_new_grid,
                                              width=self.button_width,
                                              padx=6,
                                              pady=3)

        # min sum label
        self.sum_title = tkinter.Label(self.buttons_frame, text="Minimum Sum:")

        self.sumString = tkinter.StringVar()
        self.sumString.set("")
        self.sum_label = tkinter.Label(self.buttons_frame,
                                       textvariable=self.sumString,
                                       font=('Helvetica', 15, 'bold'),
                                       pady=10)

        self.input_label.pack()
        self.input.pack(fill=tkinter.X)
        self.check_button.pack()
        self.show_button.pack()
        self.new_grid_button.pack()
        self.sum_title.pack()
        self.sum_label.pack()

        # arrange the frames
        self.grid_frame.pack(side='left')
        self.buttons_frame.pack(side='left', fill=tkinter.Y)

        # generate random grid
        self.create_new_grid()

        self.window.mainloop()

    # create new grid
    def create_new_grid(self):
        self.generate_grid()

        # update the result label
        self.sumString.set("")

        # compute the sum for each cells
        self.compute_min_sum()

        # activate the check answer button
        self.check_button['state'] = tkinter.ACTIVE

    # generate a random grid
    def generate_grid(self):
        self.grid = []
        for i in range(self.size):
            row = []
            for j in range(self.size):
                # create value and a label to store value
                value = random.randrange(1, self.max_value)
                label = tkinter.Label(self.grid_frame,
                                      text=str(value),
                                      font=('Helvetica', 15, 'bold'),
                                      borderwidth=1,
                                      relief='solid',
                                      width=6,
                                      height=3,
                                      bg='lightgray')
                label.grid(row=i, column=j)

                # add element to the row
                element = {
                    'value': value,
                    'label': label,
                    'sum': None,
                }
                row.append(element)

            self.grid.append(row)

    # show the path
    def show_path(self):
        self.mark_path(self.size-1, self.size-1)
        self.sumString.set(str(self.grid[-1][-1]['sum']))

        # disable the check answer
        self.check_button['state'] = tkinter.DISABLED

    # mark the cells in the path to be yellow
    def mark_path(self, x, y):
        element = self.grid[x][y]
        label = element['label']

        # mark the cell belonging to the path
        label.config(bg='yellow')

        # base case
        if x == 0 and y == 0:
            return

        # get the sums of the left anh upper cells
        left_sum = self.max_value * (self.size ** 2)
        up_sum = left_sum
        if x > 0:
            left_sum = self.grid[x-1][y]['sum']
        if y > 0:
            up_sum = self.grid[x][y-1]['sum']

        # sum of the previous cell on the path
        previous_sum = element['sum'] - element['value']

        # mark previous cells
        if previous_sum == left_sum:
            self.mark_path(x - 1, y)
        elif previous_sum == up_sum:
            self.mark_path(x, y - 1)

    # compute the min sum for position (x, y)
    def compute_min_sum(self):
        for x in range(self.size):
            for y in range(self.size):
                if x == 0 and y == 0:
                    self.grid[0][0]['sum'] = self.grid[0][0]['value']
                    continue

                # get the sums of the left anh upper position
                left_sum = self.max_value * (self.size ** 2)
                up_sum = left_sum
                if x > 0:
                    left_sum = self.grid[x-1][y]['sum']
                if y > 0:
                    up_sum = self.grid[x][y-1]['sum']

                self.grid[x][y]['sum'] = min(left_sum, up_sum) + \
                    self.grid[x][y]['value']

    def check_answer(self):
        result = self.grid[-1][-1]['sum']
        answer = int(self.input.get())

        message = "Wrong answer! Try Again!"
        if answer == result:
            message = "Congratulations! You are so smart!"
            self.show_path()

        tkinter.messagebox.showinfo(title='Result',
                                    message=message)
