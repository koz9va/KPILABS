set nocompatible
filetype off
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

Plugin 'VundleVim/Vundle.vim'

Plugin 'octol/vim-cpp-enhanced-highlight'
Plugin 'altercation/vim-colors-solarized'
Plugin 'scrooloose/nerdtree'
Plugin 'majutsushi/tagbar'
Plugin 'easymotion/vim-easymotion'
Plugin 'tpope/vim-surround'
Plugin 'frazrepo/vim-rainbow'
Plugin 'vhdirk/vim-cmake'
Plugin 'raimondi/delimitmate'
Plugin 'godlygeek/tabular'
Plugin 'c.vim'
Plugin 'nathanaelkane/vim-indent-guides'
Plugin 'b3niup/numbers.vim'
Plugin 'jvirtanen/vim-octave'


call vundle#end()
filetype plugin indent on
syntax enable
set background=dark
colorscheme molokai
map <silent> <C-e> :NERDTreeToggle<CR>
map <silent> <C-m> :set mouse=a<CR>

let g:cpp_class_scope_highlight = 1
let g:cpp_member_variable_highlight = 1
let g:cpp_class_decl_hightlight = 1
let g:cpp_experimental_simple_template_highlight = 1

"let g:indent_guides_enable_on_vim_startup = 1
"let g:indent_guides_guide_size = 1

let g:rainbow_active = 1
let g:rainbow_guifg = ['lightblue', 'DarkOrange3', 'DarkOrchid3', 'FireBrick']
let g:rainbow_ctermfgs = ['lightblue', 'lightgreen', 'yellow', 'red', 'magenta']

let g:numbers_exclude = ['nerdtree', 'html']
let g:numbers_exclude = ['minibufexpl', 'nerdtree', 'unite', 'tagbar', 'startify', 'gundo', 'vimshell', 'w3m']

let g:C_UseToll_cmake = 'yes'

