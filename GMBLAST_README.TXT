Sierra General MIDI Driver w/ SFX support

Supports General MIDI and digital sound effects in earlier SCI1-based Sierra games. The following games have been tested to work with this driver:

    Space Quest 1 VGA
    Leisure Suit Larry 1 VGA
    Leisure Suit Larry 5
    Police Quest 3 (some MIDI instruments are incorrect)


Most Sierra's SCI1 games have MIDI music with digital sound effects. Depending on the version of the interpreter, this can be supported in several ways. In later SCI1 interpreters (such as Space Quest 5, Police Quest 1, and Conquests of the Longbow) Sierra used separate drivers for music and sound effects. However in earlier interpreters (such as Space Quest 1 and Leisure Suit Larry 5) the logic to handle both music and sound effects were in the same driver.

To receive the best-quality MIDI music, a General MIDI (GM) compatible wavetable synthesizer is recommended over a FM synth. Wavetable synths of the day were the Roland MT-32 (though it was not GM compatible) and the Sound Blaster AWE32 while popular FM synths were the Adlib and Sound Blaster 16. Furthermore, the Sound Blaster series were popularized by thier ability to playback digitized sound effects. Today, all sound cards can playback recorded sounds and nearly all are GM compatible. But back in the day, the only way to do both was to buy an AWE32 (which wasn't available until the mid-90's) or connect an MT-32 to an SB card. Furthermore, the game had to have the correct drivers to support these options.

In those earlier SCI1 games, if you want to hear the digitized sound effects, you have two options:

    Use the SNDBLAST.DRV driver (for SB cards) which uses the FM synth for MIDI music.

    Use the MTBLAST.DRV driver (for an MT-32 connected to an SB) which expects to talk 
    to a real MT-32. MT-32 used a different instrument mapping than GM so the music 
    will sound all wrong.

Unfortunately, there was no way to have GM-compatible MIDI music and digital sound effects. This driver, GMBLAST.DRV, corrects this problem.


Driver assembler code documentation included. GMBLAST.DRV is nothing more than GENMIDI.DRV with the sound effects code appended from MTBLAST.DRV (and some supporting logic). You're welcome to use it for your own debugging purposes.


Install Instructions

    Copy the gmblast files into the game directory.
    Edit the resource.cfg file and change the value of the soundDrv parameter to GMBLAST.DRV.
    Optionally You can append the following lines to install.hlp so the install program will 
    recognize the driver:

            \\gmblast.drv
            General MIDI with SoundBlaster
            \\gmblast.hlp
            General MIDI Device with SoundBlaster Sound Effects
        

This work was done entirely in assembly language using MS-DOS debug, hiew, Micro$oft Word, and DosBox. Special thanks to Ravi Iyengar, whose framework soundriver code helped me greatly. Please send questions and comments to voyager@voyd.net. Donations are welcome and a good motivation tool.

Michael C. Maggio, voyd.net
