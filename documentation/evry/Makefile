# To use this have MinGW installed and the dll's: msys-1.0.dll, cygintl-2.dll
# Try to find out why I can't use 'rm -f *.aux' instead of 'rm -f sofia.aux'
#
#	Usage:
#		make            - normal latex compile with clean
#		make noclean    - normal latex compile with no clean
#		make clean      - remove all tmp files
#		make landscape  - compile the tex in landscape format
#
UNAME = $(shell uname)

first : NAME = evry-mickelson

ifneq ($(UNAME), Linux)
	ifeq ($(UNAME), MINGW32_NT-5.1)
		UNAME = Linux
	else
		UNAME = Windows
	endif
endif

ifeq ($(UNAME), Linux)
	DEL_FILE = rm -f
	CP_FILE = cp
else
	DEL_FILE = del /Q
	CP_FILE = copy
endif

PS_FILE = $(NAME).ps
PDF_FILE = $(NAME).pdf
TEX_FILE = $(NAME).tex
OUTDIR = output
VIM = vi
LATEX = latex
DVIPS = dvips
PS2PDF = ps2pdf

first: $(PDF_FILE)
en: $(PDF_FILE)

$(PS_FILE): 
	$(LATEX) $(TEX_FILE)
	$(DVIPS) -o $(OUTDIR)/$(PS_FILE) $(NAME).dvi
	$(LATEX) $(TEX_FILE)
	$(DVIPS) -o $(OUTDIR)/$(PS_FILE) $(NAME).dvi

$(PDF_FILE): $(PS_FILE)
	$(PS2PDF) $(OUTDIR)/$(PS_FILE) $(OUTDIR)/$(PDF_FILE)
	-$(DEL_FILE) *.aux *.log *.dvi *.toc *.out

noclean: $(PS_FILE)
	$(PS2PDF) $(OUTDIR)/$(PS_FILE) $(OUTDIR)/$(PDF_FILE)

clean:
	-$(DEL_FILE) *.aux *.log *.dvi *.toc *.out
