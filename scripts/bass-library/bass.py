import ctypes

class Bass:
    bass_module = ctypes.CDLL('libbass.dylib', mode=ctypes.RTLD_GLOBAL)
    func_type = ctypes.CFUNCTYPE

    HSTREAM = ctypes.c_ulong
    HSYNC = ctypes.c_ulong
    QWORD = ctypes.c_int64

    BASS_SYNC_END = 2
    BASS_POS_BYTE = 0

    # BASS_ChannelIsActive return values
    BASS_ACTIVE_STOPPED = 0
    BASS_ACTIVE_PLAYING = 1
    BASS_ACTIVE_STALLED = 2
    BASS_ACTIVE_PAUSED = 3

    DOWNLOADPROC = func_type(ctypes.c_void_p, ctypes.c_void_p, ctypes.c_ulong, ctypes.c_void_p)     
    BASS_Init = func_type(ctypes.c_bool, ctypes.c_int, ctypes.c_ulong, ctypes.c_ulong, ctypes.c_void_p, ctypes.c_void_p)(('BASS_Init', bass_module))
    BASS_Free = func_type(ctypes.c_bool)(('BASS_Free', bass_module))
    BASS_StreamCreateFile = func_type(HSTREAM, ctypes.c_bool, ctypes.c_void_p, QWORD, QWORD, ctypes.c_ulong)(('BASS_StreamCreateFile', bass_module))
    BASS_StreamCreateURL = func_type(HSTREAM, ctypes.c_char_p, ctypes.c_ulong, ctypes.c_ulong, DOWNLOADPROC, ctypes.c_void_p)(('BASS_StreamCreateURL', bass_module))
    BASS_StreamFree = func_type(ctypes.c_bool, HSTREAM)(('BASS_StreamFree', bass_module))
    BASS_ChannelPlay = func_type(ctypes.c_bool, ctypes.c_ulong, ctypes.c_bool)(('BASS_ChannelPlay', bass_module))
    BASS_ChannelStop = func_type(ctypes.c_bool, ctypes.c_ulong)(('BASS_ChannelStop', bass_module))
    BASS_ChannelPause = func_type(ctypes.c_bool, ctypes.c_ulong)(('BASS_ChannelPause', bass_module))
    BASS_ErrorGetCode = func_type(ctypes.c_int)(('BASS_ErrorGetCode', bass_module))
    BASS_Free = func_type(ctypes.c_bool)(('BASS_Free', bass_module))
    BASS_ChannelIsActive = func_type(ctypes.c_ulong, ctypes.c_ulong)(('BASS_ChannelIsActive', bass_module))
    BASS_SetDevice = func_type(ctypes.c_bool, ctypes.c_ulong)(('BASS_SetDevice', bass_module))